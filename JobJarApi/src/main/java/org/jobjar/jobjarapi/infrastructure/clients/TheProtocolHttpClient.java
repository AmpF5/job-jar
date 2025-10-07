package org.jobjar.jobjarapi.infrastructure.clients;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.jobjar.jobjarapi.domain.models.responses.TheProtocolResponse;
import org.jobjar.jobjarapi.infrastructure.services.HttpClientPropertiesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.List;

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
        return List.of();
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
}
