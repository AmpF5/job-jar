package org.jobjar.jobjarapi.infrastructure.clients;

import org.jobjar.jobjarapi.domain.configuration.HttpClientName;
import org.jobjar.jobjarapi.infrastructure.HttpClientPropertiesService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class JustJoinItHttpClient implements BaseClient {
    private static final HttpClientName HTTP_CLIENT_NAME = HttpClientName.JUST_JOIN_IT;
    private final HttpClientPropertiesService httpClientPropertiesService;
    private final HttpClient httpClient;

    public JustJoinItHttpClient(HttpClientPropertiesService httpClientPropertiesService1) {
        this.httpClientPropertiesService = httpClientPropertiesService1;
        this.httpClient = buildHttpClient();
    }

    @Override
    public void getRequest() {
        var req = buildRequest();

        try {
            var resp = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
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
        var request = HttpRequest.newBuilder(httpClientPropertiesService.getUri(HTTP_CLIENT_NAME))
                .GET();

        httpClientPropertiesService.getHeaders(HTTP_CLIENT_NAME).forEach(request::header);

        return request.build();
    }
}
