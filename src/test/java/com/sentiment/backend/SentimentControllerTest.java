package com.sentiment.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SentimentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // --- Tests de Main (Health Check) ---
    @Test
    void healthCheck_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk());
    }

    // --- Tests de Dev 4 (Validaciones y Lógica) ---
    @Test
    void whenValidText_thenReturns200() throws Exception {
        // Simulamos un request válido con texto
        String jsonRequest = "{\"text\": \"Este es un gran proyecto de hackathon\"}";

        mockMvc.perform(post("/sentiment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prevision").exists());
    }

    @Test
    void whenEmptyText_thenReturns400() throws Exception {
        String jsonRequest = "{\"text\": \"\"}";

        mockMvc.perform(post("/sentiment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSentimentAnalysis_BadRequest_TooShort() throws Exception {
        // Caso error: Texto muy corto (< 5 caracteres)
        String jsonRequest = "{\"text\": \"Hola\"}";

        mockMvc.perform(post("/sentiment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testModelHealth() throws Exception {
        mockMvc.perform(get("/health/model"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"));
    }

    @Test
    void testStats() throws Exception {
        mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").exists());
    }

    @Test
    void testHistory() throws Exception {
        mockMvc.perform(get("/history"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}