package com.aztu68.spring.greenwich.genesis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class SimpleWebTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void pageNotFound() {

        var response = restTemplate.getForEntity("/bogusPage", String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
