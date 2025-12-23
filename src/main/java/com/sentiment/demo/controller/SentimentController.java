package com.sentiment.demo.controller;

import com.sentiment.demo.dto.Prevision;
import com.sentiment.demo.dto.SentimentRequest;
import com.sentiment.demo.dto.SentimentResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sentiment") // La URL será: http://localhost:8080/sentiment
public class SentimentController {

    @PostMapping
    public ResponseEntity<?> analizar(
            @Valid @RequestBody SentimentRequest request) {

        // Aquí solo entra texto válido (gracias a @Valid)

        SentimentResponse response =
                new SentimentResponse(Prevision.NEGATIVO, 0.40);

        return ResponseEntity.ok(response);
    }
}