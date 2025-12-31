package com.sentiment.demo.controller;

import com.sentiment.demo.dto.SentimentRequest;
import com.sentiment.demo.dto.SentimentResponse;
import com.sentiment.demo.service.SentimentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sentiment")
public class SentimentController {

    private final SentimentService sentimentService;

    public SentimentController(SentimentService sentimentService) {
        this.sentimentService = sentimentService;
    }

    @PostMapping
    public ResponseEntity<SentimentResponse> analizar(@Valid @RequestBody SentimentRequest request) {
        SentimentResponse response = sentimentService.predict(request.text().trim());
        return ResponseEntity.ok(response);
    }

}