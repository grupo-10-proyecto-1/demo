package com.sentiment.demo.client;

import com.sentiment.demo.dto.Prevision;
import com.sentiment.demo.dto.SentimentResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Component
public class SentimentDsClient {
    private final RestTemplate restTemplate;

    public SentimentDsClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Como no sabemos como es el DS real usaremos uno falso para que se pueda probar

    public SentimentResponse predict(String text){
        return new SentimentResponse(Prevision.POSITIVO, 0.95);
    }

}
