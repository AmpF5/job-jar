package org.jobjar.jobjarapi.infrastructure.connectors;

import org.jobjar.jobjarapi.infrastructure.clients.BaseHttpClient;
import org.jobjar.jobjarapi.infrastructure.clients.JustJoinItHttpClient;
import org.springframework.stereotype.Service;


@Service
public class JustJoinItConnector implements BaseConnector {
    private final BaseHttpClient baseClient;
    public JustJoinItConnector(JustJoinItHttpClient client) {
        baseClient = client;
    }

    @Override
    public void getOffers() {
        baseClient.getRequest();
    }
}