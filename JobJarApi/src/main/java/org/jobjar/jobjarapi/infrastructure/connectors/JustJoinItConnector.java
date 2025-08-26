package org.jobjar.jobjarapi.infrastructure.connectors;

import org.jobjar.jobjarapi.infrastructure.clients.BaseClient;
import org.jobjar.jobjarapi.infrastructure.clients.JustJoinItHttpClient;
import org.springframework.stereotype.Service;


@Service
public class JustJoinItConnector implements BaseConnector {
    private final BaseClient baseClient;
    public JustJoinItConnector(JustJoinItHttpClient client) {
        baseClient = client;
    }

    @Override
    public void GetOffers() {
        baseClient.GetRequest();
    }
}