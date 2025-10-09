package org.jobjar.jobjarapi.infrastructure.clients;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jobjar.jobjarapi.domain.models.responses.TheProtocolResponse;
import org.jobjar.jobjarapi.infrastructure.services.HttpClientPropertiesService;
import org.jobjar.jobjarapi.utils.JsonParser;
import org.jobjar.jobjarapi.utils.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TheProtocolHttpClient implements BaseHttpClient<TheProtocolResponse.TheProtocolOffer>, BaseHttpClientBuilder {
    private final static Logger log = LoggerFactory.getLogger(TheProtocolHttpClient.class);
    private final HttpClientPropertiesService httpClientPropertiesService;
    private final HttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public TheProtocolHttpClient(@Qualifier("theprotocol") HttpClientPropertiesService httpClientPropertiesService) {
        this.httpClientPropertiesService = httpClientPropertiesService;
        this.httpClient = buildHttpClient();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<TheProtocolResponse.TheProtocolOffer> getRequest() {
        log.info("Getting data from theprotocol.it");

        var start = System.nanoTime();

        var firstReq = buildRequest(httpClientPropertiesService.getUri());

        try  {
            return httpClient.sendAsync(firstReq, HttpResponse.BodyHandlers.ofString())
                    .thenApply(firstResp -> {
                        try {
                            return mapper.readValue(firstResp.body(), TheProtocolResponse.class);
                        } catch (JsonProcessingException e) {
                            log.error("Error while mapping data [{}]", firstResp.statusCode(), e);
                            throw new CompletionException(e);
                        }
                    })
                    .thenCompose(firstRespMapped -> {
                        log.info("Number of pages to fetch {}", firstRespMapped.getPage().getSize());

                        var uris = getAllUris(firstRespMapped.getPage().getSize());

                        var requests = uris
                                .stream()
                                .map(this::buildRequest)
                                .map(req ->
                                    httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                                            .thenApply(resp ->
                                                JsonParser.parse(mapper, resp, TheProtocolResponse.class)
                                            )
                                )
                                .collect(Collectors.toList());

                        requests.add(CompletableFuture.completedFuture(firstRespMapped));

                        return CompletableFuture.allOf(requests.toArray(new CompletableFuture[]{}))
                                .thenApply(ao -> requests
                                        .stream()
                                        .map(CompletableFuture::join)
                                        .toList());

                    })
                    .thenApply(values -> values
                            .stream()
                            .flatMap(offer -> offer.getOffers().stream())
                            .toList()
                    )
                    .orTimeout(20, TimeUnit.SECONDS)
                    .whenComplete((result, err) -> {
                        if(err != null) {
                            var cause =  err.getCause();
                            log.error("Error while fetching data {}", cause.getMessage(), cause);
                        } else {
                            var end = System.nanoTime();
                            log.info("Successfully fetched data in {} ms", TimeConverter.getElapsedTime(start, end));
                            log.info("Number of job offers {}", result.size());
                        }
                    })
                    .join();

        } catch (CompletionException e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public HttpClient buildHttpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }

    @Override
    public HttpRequest buildRequest(URI uri) {
        var req = HttpRequest
                .newBuilder(uri)
                .POST(HttpRequest.BodyPublishers.noBody());

        httpClientPropertiesService
                .getHeaders()
                .forEach(req::setHeader);

        return req.build();
    }

    private List<URI> getAllUris(int numberOfPages) {
        var uriBuilder = UriComponentsBuilder
                .fromUri(httpClientPropertiesService.getUri());

         return IntStream
                .iterate(2,x -> x + 1)
                .limit(numberOfPages)
                .boxed()
                .map(x -> {
                    var uriWithPage = (UriComponentsBuilder) uriBuilder.clone();
                    uriWithPage.replaceQueryParam("pageNumber", x);
                    return uriWithPage.build().toUri();
                })
                .toList();
    }
}
