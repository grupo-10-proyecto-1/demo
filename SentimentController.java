package com.example.demo.controller;

import com.example.demo.dto.SentimentRequest;
import com.example.demo.dto.StatResponseDTO;
import com.example.demo.model.SentimentStat;
import com.example.demo.repository.SentimentStatRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SentimentController {

    private final RestTemplate restTemplate;
    
    @Value("${sentiment.mode:mock}")
    private String mode;
    
    @Value("${python.api.url}")
    private String pythonApiUrl;

    private final SentimentStatRepository repository;

    public SentimentController(RestTemplate restTemplate, SentimentStatRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @PostMapping("/sentiment")
    public ResponseEntity<?> analyzeSentiment(@RequestBody SentimentRequest request) {
        // Dev 2: Validaciones API friendly
        if (request.getText() == null || request.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'text' es obligatorio.");
        }
        if (request.getText().length() > 2000) {
            throw new IllegalArgumentException("El texto excede el límite de 2000 caracteres.");
        }

        Map<String, Object> response;

        if ("python".equalsIgnoreCase(mode)) {
            // Dev 1: Proxy mode
            response = restTemplate.postForObject(pythonApiUrl, request, Map.class);
            
            // Persistencia (Dev 1): Solo si es modo python y respuesta exitosa
            if (response != null) {
                SentimentStat stat = new SentimentStat();
                stat.setText(request.getText());
                stat.setPrevision((String) response.getOrDefault("prevision", "NEUTRO"));
                // Manejo seguro de tipos para probabilidad
                Object prob = response.getOrDefault("probabilidad", 0.0);
                stat.setProbabilidad(prob instanceof Number ? ((Number) prob).doubleValue() : 0.0);
                repository.save(stat);
            }
        } else {
            // Mock mode
            String sentiment = mockAnalyze(request.getText());
            response = Map.of(
                "prevision", sentiment,
                "probabilidad", 0.95,
                "mode", "mock"
            );
        }

        return ResponseEntity.ok(response);
    }

    // Dev 1: Health check del modelo
    @GetMapping("/health/model")
    public ResponseEntity<?> checkModelHealth() {
        if ("python".equalsIgnoreCase(mode)) {
            try {
                // Asumimos que el servicio Python tiene un endpoint raíz o health
                // Para este ejemplo hacemos un ping simple
                restTemplate.getForEntity(pythonApiUrl.replace("/predict", ""), String.class);
                return ResponseEntity.ok(Map.of("status", "UP", "mode", "python"));
            } catch (Exception e) {
                return ResponseEntity.status(503).body(Map.of("status", "DOWN", "error", e.getMessage()));
            }
        }
        return ResponseEntity.ok(Map.of("status", "OK", "mode", "mock"));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> checkHealth() {
        return ResponseEntity.ok(Map.of("status", "UP", "message", "Backend operativo"));
    }

    // Dev 1: Endpoint de estadísticas con persistencia
    @GetMapping("/stats")
    public ResponseEntity<StatResponseDTO> getStats(@RequestParam(defaultValue = "100") int cantidad) {
        // Limitar cantidad para seguridad
        int limit = Math.min(cantidad, 1000);
        
        List<SentimentStat> stats = repository.findLastStats(PageRequest.of(0, limit));
        
        long total = stats.size();
        Map<String, Long> breakdown = new HashMap<>();
        breakdown.put("POSITIVO", stats.stream().filter(s -> "POSITIVO".equalsIgnoreCase(s.getPrevision())).count());
        breakdown.put("NEGATIVO", stats.stream().filter(s -> "NEGATIVO".equalsIgnoreCase(s.getPrevision())).count());
        breakdown.put("NEUTRO", stats.stream().filter(s -> "NEUTRO".equalsIgnoreCase(s.getPrevision())).count());

        Map<String, Double> percentages = new HashMap<>();
        if (total > 0) {
            percentages.put("POSITIVO", Math.round((breakdown.get("POSITIVO") * 100.0 / total) * 100.0) / 100.0);
            percentages.put("NEGATIVO", Math.round((breakdown.get("NEGATIVO") * 100.0 / total) * 100.0) / 100.0);
            percentages.put("NEUTRO", Math.round((breakdown.get("NEUTRO") * 100.0 / total) * 100.0) / 100.0);
        }

        return ResponseEntity.ok(new StatResponseDTO(limit, total, breakdown, percentages));
    }

    // Dev 1: Endpoint de historial
    @GetMapping("/history")
    public ResponseEntity<List<SentimentStat>> getHistory(@RequestParam(defaultValue = "10") int limit) {
        int safeLimit = Math.min(limit, 100);
        return ResponseEntity.ok(repository.findLastStats(PageRequest.of(0, safeLimit)));
    }

    private String mockAnalyze(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("excelente") || lower.contains("bueno")) return "POSITIVO";
        if (lower.contains("falla") || lower.contains("malo")) return "NEGATIVO";
        return "NEUTRO";
    }
}
package com.example.demo.controller;

import com.example.demo.dto.SentimentRequest;
import com.example.demo.dto.StatResponseDTO;
import com.example.demo.model.SentimentStat;
import com.example.demo.repository.SentimentStatRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SentimentController {

    private final RestTemplate restTemplate;
    
    @Value("${sentiment.mode:mock}")
    private String mode;
    
    @Value("${python.api.url}")
    private String pythonApiUrl;

    private final SentimentStatRepository repository;

    public SentimentController(RestTemplate restTemplate, SentimentStatRepository repository) {
        this.restTemplate = restTemplate;
        this.repository = repository;
    }

    @PostMapping("/sentiment")
    public ResponseEntity<?> analyzeSentiment(@RequestBody SentimentRequest request) {
        // Dev 2: Validaciones API friendly
        if (request.getText() == null || request.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("El campo 'text' es obligatorio.");
        }
        if (request.getText().length() > 2000) {
            throw new IllegalArgumentException("El texto excede el límite de 2000 caracteres.");
        }

        Map<String, Object> response;

        if ("python".equalsIgnoreCase(mode)) {
            // Dev 1: Proxy mode
            response = restTemplate.postForObject(pythonApiUrl, request, Map.class);
            
            // Persistencia (Dev 1): Solo si es modo python y respuesta exitosa
            if (response != null) {
                SentimentStat stat = new SentimentStat();
                stat.setText(request.getText());
                stat.setPrevision((String) response.getOrDefault("prevision", "NEUTRO"));
                // Manejo seguro de tipos para probabilidad
                Object prob = response.getOrDefault("probabilidad", 0.0);
                stat.setProbabilidad(prob instanceof Number ? ((Number) prob).doubleValue() : 0.0);
                repository.save(stat);
            }
        } else {
            // Mock mode
            String sentiment = mockAnalyze(request.getText());
            response = Map.of(
                "prevision", sentiment,
                "probabilidad", 0.95,
                "mode", "mock"
            );
        }

        return ResponseEntity.ok(response);
    }

    // Dev 1: Health check del modelo
    @GetMapping("/health/model")
    public ResponseEntity<?> checkModelHealth() {
        if ("python".equalsIgnoreCase(mode)) {
            try {
                // Asumimos que el servicio Python tiene un endpoint raíz o health
                // Para este ejemplo hacemos un ping simple
                restTemplate.getForEntity(pythonApiUrl.replace("/predict", ""), String.class);
                return ResponseEntity.ok(Map.of("status", "UP", "mode", "python"));
            } catch (Exception e) {
                return ResponseEntity.status(503).body(Map.of("status", "DOWN", "error", e.getMessage()));
            }
        }
        return ResponseEntity.ok(Map.of("status", "OK", "mode", "mock"));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> checkHealth() {
        return ResponseEntity.ok(Map.of("status", "UP", "message", "Backend operativo"));
    }

    // Dev 1: Endpoint de estadísticas con persistencia
    @GetMapping("/stats")
    public ResponseEntity<StatResponseDTO> getStats(@RequestParam(defaultValue = "100") int cantidad) {
        // Limitar cantidad para seguridad
        int limit = Math.min(cantidad, 1000);
        
        List<SentimentStat> stats = repository.findLastStats(PageRequest.of(0, limit));
        
        long total = stats.size();
        Map<String, Long> breakdown = new HashMap<>();
        breakdown.put("POSITIVO", stats.stream().filter(s -> "POSITIVO".equalsIgnoreCase(s.getPrevision())).count());
        breakdown.put("NEGATIVO", stats.stream().filter(s -> "NEGATIVO".equalsIgnoreCase(s.getPrevision())).count());
        breakdown.put("NEUTRO", stats.stream().filter(s -> "NEUTRO".equalsIgnoreCase(s.getPrevision())).count());

        Map<String, Double> percentages = new HashMap<>();
        if (total > 0) {
            percentages.put("POSITIVO", Math.round((breakdown.get("POSITIVO") * 100.0 / total) * 100.0) / 100.0);
            percentages.put("NEGATIVO", Math.round((breakdown.get("NEGATIVO") * 100.0 / total) * 100.0) / 100.0);
            percentages.put("NEUTRO", Math.round((breakdown.get("NEUTRO") * 100.0 / total) * 100.0) / 100.0);
        }

        return ResponseEntity.ok(new StatResponseDTO(limit, total, breakdown, percentages));
    }

    // Dev 1: Endpoint de historial
    @GetMapping("/history")
    public ResponseEntity<List<SentimentStat>> getHistory(@RequestParam(defaultValue = "10") int limit) {
        int safeLimit = Math.min(limit, 100);
        return ResponseEntity.ok(repository.findLastStats(PageRequest.of(0, safeLimit)));
    }

    private String mockAnalyze(String text) {
        String lower = text.toLowerCase();
        if (lower.contains("excelente") || lower.contains("bueno")) return "POSITIVO";
        if (lower.contains("falla") || lower.contains("malo")) return "NEGATIVO";
        return "NEUTRO";
    }
}