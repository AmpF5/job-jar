package org.jobjar.jobjarapi.infrastructure.clients;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;

interface BaseHttpClientBuilder {
    HttpClient buildHttpClient();

    HttpRequest buildRequest();
}
