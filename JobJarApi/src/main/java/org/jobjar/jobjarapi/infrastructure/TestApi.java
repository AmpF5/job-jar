package org.jobjar.jobjarapi.infrastructure;

import org.jobjar.jobjarapi.infrastructure.connectors.JustJoinItConnector;
import org.jobjar.jobjarapi.infrastructure.services.HttpClientPropertiesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {
    private final JustJoinItConnector justJoinItConnector;

    public TestApi(HttpClientPropertiesService httpClientPropertiesService, JustJoinItConnector justJoinItConnector) {
        this.justJoinItConnector = justJoinItConnector;
    }

    @GetMapping()
    public void Test() {
        justJoinItConnector.getOffers();
    }
}
