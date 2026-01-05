package com.example.demo.controller;

import com.example.demo.dto.SentimentRequest;
import com.example.demo.model.SentimentStat;
import com.example.demo.repository.SentimentStatRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SentimentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Dev 4: Mockeamos el repositorio para aislar la prueba del controlador
    @MockBean
    private SentimentStatRepository repository;

    @Test
    public void testAnalyzeSentiment_Success() throws Exception {
        SentimentRequest request = new SentimentRequest();
        request.setText("Este es un producto excelente y maravilloso");

        mockMvc.perform(post("/sentiment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                // Dev 4: Actualizamos validación según contrato de Dev 1 (prevision)
                .andExpect(jsonPath("$.prevision").value("POSITIVO"))
                .andExpect(jsonPath("$.mode").exists());
    }

    @Test
    public void testAnalyzeSentiment_EmptyText_Returns400() throws Exception {
        SentimentRequest request = new SentimentRequest();
        request.setText(""); // Texto vacío

        mockMvc.perform(post("/sentiment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testHealthCheck() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    public void testStats() throws Exception {
        // Dev 4: Simulamos respuesta vacía del repo para evitar NullPointerException
        when(repository.findLastStats(any(Pageable.class))).thenReturn(List.of());

        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                // Dev 4: Validamos el campo 'total' del nuevo DTO
                .andExpect(jsonPath("$.total").exists());
    }

    @Test
    public void testHistory() throws Exception {
        // Dev 4: Validamos que el historial devuelva una lista
        when(repository.findLastStats(any(Pageable.class))).thenReturn(List.of(new SentimentStat()));

        mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testModelHealth() throws Exception {
        mockMvc.perform(get("/health/model"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }
}