package org.jobjar.jobjarapi.infrastructure;

import org.jobjar.jobjarapi.infrastructure.connectors.JustJoinItConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {
    private final JustJoinItConnector justJoinItConnector;

    public TestApi(JustJoinItConnector justJoinItConnector) {
        this.justJoinItConnector = justJoinItConnector;
    }

    @GetMapping()
    public void Test() {
        justJoinItConnector.getOffers();
    }
}
