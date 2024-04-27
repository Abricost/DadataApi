package ru.example.DataEntryService.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@Profile("default")
public class RestConfiguration {

    @Value("${rest.timeout.read}")
    private int readTimeout;

    @Value("${rest.timeout.connect}")
    private int connectionTimeout;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setReadTimeout(Duration.ofSeconds(readTimeout))
                .setConnectTimeout(Duration.ofSeconds(connectionTimeout))
                .build();
    }

}
