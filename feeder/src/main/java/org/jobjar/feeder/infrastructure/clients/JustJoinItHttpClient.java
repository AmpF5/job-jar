package org.jobjar.feeder.infrastructure.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import models.responses.JustJoinItResponse;
import org.jobjar.feeder.infrastructure.services.HttpClientPropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import utils.JsonParser;
import utils.TimeConverter;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class JustJoinItHttpClient implements BaseHttpClient<JustJoinItResponse.JustJoinItJob>, BaseHttpClientBuilder {
    private final static Logger log = LoggerFactory.getLogger(JustJoinItHttpClient.class);
    private static final int TIMEOUT_SECONDS = 20;
    private final HttpClientPropertiesService httpClientPropertiesService;
    private final HttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public JustJoinItHttpClient(@Qualifier("justjoinit") HttpClientPropertiesService httpClientPropertiesService) {
        this.httpClientPropertiesService = httpClientPropertiesService;
        this.httpClient = buildHttpClient();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public List<JustJoinItResponse.JustJoinItJob> getRequest() {
        log.info("Getting data from justjoin.it");

        var start = System.nanoTime();

        var req = buildRequest(httpClientPropertiesService.getUri());

        try {
            return httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString())
                    .thenApply(firstResp -> JsonParser.parse(mapper, firstResp, JustJoinItResponse.class))
                    .thenCompose(firstRespMapped -> {
                        log.info("Number of pages to fetch {}", firstRespMapped.getMeta().getTotalPages());

                        var uris = getAllJustJoinItUris(firstRespMapped.getMeta().getTotalPages());

                        var requests = uris
                                .stream()
                                .map(this::buildRequest)
                                .map(x -> httpClient
                                        .sendAsync(x, HttpResponse.BodyHandlers.ofString())
                                        .thenApply(resp -> JsonParser.parse(mapper, resp, JustJoinItResponse.class))
                                )
                                .collect(Collectors.toList());

                        requests.add(CompletableFuture.completedFuture(firstRespMapped));

                        return CompletableFuture
                                .allOf(requests.toArray(new CompletableFuture[]{}))
                                .thenApply(ao -> requests
                                        .stream()
                                        .map(CompletableFuture::join)
                                        .toList());
                    })
                    .thenApply(values -> values
                            .stream()
                            .flatMap(offer -> offer.getData().stream())
                            .toList())
                    .orTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
                    .whenComplete((result, err) -> {
                        if (err != null) {
                            var cause = err.getCause();
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

    public HttpClient buildHttpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }

    public HttpRequest buildRequest(URI uri) {
        var request = HttpRequest
                .newBuilder(uri)
                .GET();

        httpClientPropertiesService
                .getHeaders()
                .forEach(request::header);

        return request.build();
    }

    private List<URI> getAllJustJoinItUris(int numberOfPages) {
        var uriBuilder = UriComponentsBuilder
                .fromUri(httpClientPropertiesService.getUri());

        return IntStream
                .iterate(2, x -> x + 1)
                .limit(numberOfPages)
                .boxed()
                .map(x -> {
                    var uriWithPage = (UriComponentsBuilder) uriBuilder.clone();
                    uriWithPage.queryParam("page", x);
                    return uriWithPage.build().toUri();
                })
                .toList();
    }

}