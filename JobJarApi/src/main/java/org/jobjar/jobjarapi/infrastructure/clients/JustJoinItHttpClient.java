package org.jobjar.jobjarapi.infrastructure.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jobjar.jobjarapi.domain.responses.JustJoinItResponse;
import org.jobjar.jobjarapi.infrastructure.services.HttpClientPropertiesService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class JustJoinItHttpClient implements BaseClient {
    private final HttpClientPropertiesService httpClientPropertiesService;
    private final HttpClient httpClient;
    private final ObjectMapper mapper = new ObjectMapper();

    public JustJoinItHttpClient(@Qualifier("justjoinit") HttpClientPropertiesService httpClientPropertiesService) {
        this.httpClientPropertiesService = httpClientPropertiesService;
        this.httpClient = buildHttpClient();
        mapper.registerModule(new JavaTimeModule());
    }

    @Override
    public void getRequest() {
        var req = buildRequest();

        try {
            var resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            var mappedResp = mapper.readValue(resp.body(), JustJoinItResponse.class);
            System.out.println(resp.body());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpClient buildHttpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
    }

    private HttpRequest buildRequest() {
        var request = HttpRequest.newBuilder(httpClientPropertiesService.getUri())
                .GET();

        httpClientPropertiesService.getHeaders().forEach(request::header);

        return request.build();
    }
}
