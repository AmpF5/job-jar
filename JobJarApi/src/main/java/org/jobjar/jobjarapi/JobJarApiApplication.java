package org.jobjar.jobjarapi;

import org.jobjar.jobjarapi.domain.configuration.HttpClientProperties;
import org.jobjar.jobjarapi.domain.configuration.HttpClientPropertiesConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
//@EnableConfigurationProperties(HttpClientPropertiesConfig.class)
public class JobJarApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JobJarApiApplication.class, args);
    }

}
