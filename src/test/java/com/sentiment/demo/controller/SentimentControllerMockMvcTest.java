package com.sentiment.demo.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Tests de integración para el SentimentController (Dev 4).
 * Verifica el contrato de la API y el manejo de errores básicos (400 vs 200).
 * 
 * Ejecutar con: ./mvnw -Dtest=SentimentControllerMockMvcTest test
 */
@SpringBootTest
@AutoConfigureMockMvc
public class SentimentControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSentimentAnalysis_Success() throws Exception {
        // Caso feliz: Texto válido
        String jsonRequest = "{\"text\": \"Este es un gran proyecto para la hackathon\"}";

        mockMvc.perform(post("/sentiment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prevision").exists());
    }

    @Test
    public void testSentimentAnalysis_BadRequest_Empty() throws Exception {
        // Caso error: Texto vacío (Validación Dev 2)
        String jsonRequest = "{\"text\": \"\"}";

        mockMvc.perform(post("/sentiment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }
}