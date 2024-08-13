package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.QuoteResponse;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class QouteService {
//    @Value("${quotes.api.key}")
//    private final String API_KEY = "";

    @Value("${quotes.api}")
    private String API;

    @Autowired
    private RestTemplate restTemplate;

    public QuoteResponse getQoute(){


        ResponseEntity<QuoteResponse> response = restTemplate.exchange(API, HttpMethod.GET, null, QuoteResponse.class);
        QuoteResponse body = response.getBody();
        return body;

    }
}
