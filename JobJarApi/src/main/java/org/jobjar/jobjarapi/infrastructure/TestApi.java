package org.jobjar.jobjarapi.infrastructure;

import org.jobjar.jobjarapi.domain.configuration.HttpClientName;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApi {
    private final HttpClientPropertiesService httpClientPropertiesService;

    public TestApi(HttpClientPropertiesService httpClientPropertiesService) {
        this.httpClientPropertiesService = httpClientPropertiesService;
    }

    @GetMapping()
    public void Test() {
        var x = httpClientPropertiesService.getUri(HttpClientName.JUST_JOIN_IT);
        System.out.println(x);
    }
}
