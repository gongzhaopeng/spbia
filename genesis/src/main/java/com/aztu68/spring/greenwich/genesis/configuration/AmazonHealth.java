package com.aztu68.spring.greenwich.genesis.configuration;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AmazonHealth implements HealthIndicator {

    @Override
    public Health health() {

        try {

            var restTemplate = new RestTemplate();

            restTemplate.getForObject("http://www.amazon.com", String.class);

            return Health.up().build();
        } catch (Exception e) {

            return Health.down()
                    .withDetail("reason", e.getMessage())
                    .build();
        }
    }
}
