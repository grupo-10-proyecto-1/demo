package com.sentiment.demo.service;

import com.sentiment.demo.dto.Prevision;
import com.sentiment.demo.dto.SentimentResponse;
import com.sentiment.demo.exception.ModelUnavailableException;

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
            return new SentimentResponse(Prevision.POSITIVO, 0.95);
        }

        try {

            Map<String,Object> response =
                    restTemplate.postForObject(
                            pythonUrl,
                            Map.of("text", text),
                            Map.class
                    );

            return new SentimentResponse(
                    Prevision.valueOf(response.get("prevision").toString()),
                    ((Number)response.get("probabilidad")).doubleValue()
            );

        } catch (Exception ex) {

            throw new ModelUnavailableException("Modelo no disponible", ex);
        }
    }
}
