package org.jobjar.feeder.infrastructure.clients;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

interface BaseHttpClientBuilder {
    HttpClient buildHttpClient();

    HttpRequest buildRequest(URI uri);
}
