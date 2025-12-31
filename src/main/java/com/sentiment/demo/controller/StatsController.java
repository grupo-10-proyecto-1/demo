package com.sentiment.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StatsController {

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        // TODO (Dev1 - Bernardo): implementar con persistencia (DB)
        return ResponseEntity.status(501).body(Map.of(
                "status", "NOT_IMPLEMENTED",
                "message", "Stats pendiente: se implementará con persistencia en BD"
        ));
    }
}