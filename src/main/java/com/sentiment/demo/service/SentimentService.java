package com.sentiment.demo.service;

import com.sentiment.demo.dto.SentimentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class SentimentService {
    private final RestTemplate restTemplate;
    @Value("${sentiment.mode}")
    private String mode;

    @Value("${python.api.url}")
    private String pythonUrl;

    public SentimentService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SentimentResponse predict(String text) {

        if (mode.equalsIgnoreCase("mock")) {
            return new SentimentResponse("Positivo", 0.95);
        }

        try {

            Map<String,Object> response =
                    restTemplate.postForObject(
                            pythonUrl,
                            Map.of("text", text),
                            Map.class
                    );

            return new SentimentResponse(
                    response.get("label").toString(),
                    ((Number)response.get("probability")).doubleValue()
            );

        } catch (Exception ex) {

            return new SentimentResponse(
                    "Error: Modelo no disponible",
                    0.0
            );
        }
    }
}
