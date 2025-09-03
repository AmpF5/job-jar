package org.jobjar.jobjarapi.infrastructure;

import org.jobjar.jobjarapi.infrastructure.adapters.JustJoinItAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {
    private final JustJoinItAdapter justJoinItConnector;

    public TestApi(JustJoinItAdapter justJoinItConnector) {
        this.justJoinItConnector = justJoinItConnector;
    }

    @GetMapping()
    public void Test() {
        justJoinItConnector.getOffers();
    }
}
