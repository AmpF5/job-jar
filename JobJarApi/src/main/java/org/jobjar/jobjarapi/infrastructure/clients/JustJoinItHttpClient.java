package org.jobjar.jobjarapi.infrastructure.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jobjar.jobjarapi.domain.models.responses.JustJoinItResponse;
import org.jobjar.jobjarapi.infrastructure.services.HttpClientPropertiesService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

@Service
public class JustJoinItHttpClient implements BaseHttpClient<JustJoinItResponse.JustJoinItJob>, BaseHttpClientBuilder {
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
        var req = buildRequest(httpClientPropertiesService.getUri());

        try {
            var firstResp = httpClient.sendAsync(req, HttpResponse.BodyHandlers.ofString()).get();
            var firstMappedResp = mapper.readValue(firstResp.body(), JustJoinItResponse.class);
            var numberOfPages = firstMappedResp.getMeta().getTotalPages();

            var allResponses = getAllJustJoinItResponses(getAllJustJoinItUris(numberOfPages));
            allResponses.add(firstMappedResp);

            return allResponses
                    .stream()
                    .flatMap(x -> x.getData().stream())
                    .toList();
        } catch (IOException | InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public HttpClient buildHttpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
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

    private List<JustJoinItResponse> getAllJustJoinItResponses(List<URI> uris) throws InterruptedException, ExecutionException {
        var requests = uris
                .stream()
                .map(this::buildRequest)
                .toList();

        var futureRequests = requests
                .stream()
                .map(x -> httpClient
                        .sendAsync(x, HttpResponse.BodyHandlers.ofString()
                        )
                )
                .toList();

        return CompletableFuture
                .allOf(futureRequests.toArray(new CompletableFuture<?>[0]))
                .thenApply(x -> futureRequests
                        .stream()
                        .map(CompletableFuture::join)
                        .map(y -> {
                            try {
                                return mapper.readValue(y.body(), JustJoinItResponse.class);
                            } catch (JsonProcessingException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .toList()
                )
                .get();
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