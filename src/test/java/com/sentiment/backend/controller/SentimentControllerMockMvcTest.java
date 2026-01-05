package com.sentiment.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.sentiment.backend.client.SentimentDsClient;
import com.sentiment.backend.controller.HealthController;
import com.sentiment.backend.controller.SentimentController;
import com.sentiment.backend.controller.StatsController;
import com.sentiment.backend.dto.Prevision;
import com.sentiment.backend.dto.SentimentResponse;
import com.sentiment.backend.dto.StatResponseDTO;
import com.sentiment.backend.service.SentimentService;
import com.sentiment.backend.service.StatsService;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Testeamos los controllers, especificándolos aquí.
// Spring solo creará los beans para la capa web y los que mockeemos.
@WebMvcTest(controllers = {SentimentController.class, HealthController.class, StatsController.class})
class SentimentControllerMockMvcTest {

    @Autowired
    private MockMvc mockMvc; // cliente de pruebas que simula peticiones HTTP al controller

    // Mockeamos las dependencias de nuestros controllers para aislarlos
    /**
     * Caso: si no mando el campo `text`, espero un 400 Bad Request.
     * Para qué sirve: asegura que la validación del DTO está activa y
     * que la API no acepta requests vacíos.
     */
    @MockBean
    private SentimentService sentimentService;

    @MockBean
    private SentimentDsClient sentimentDsClient; // HealthController depende de este

    @MockBean
    private StatsService statsService;


    @Test
    void whenMissingText_thenReturns400() throws Exception {
        mockMvc.perform(post("/sentiment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenTextIsTooShort_thenReturns400() throws Exception {
        var body = "{\"text\":\"hola\"}"; // Menos de 5 caracteres
        mockMvc.perform(post("/sentiment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isBadRequest());
    }

    /**
     * Caso: envío texto válido y espero 200 OK con la estructura JSON correcta.
     * Por qué lo hacemos: confirma que el controller procesa peticiones válidas.
     */
    @Test
    void whenValidText_thenReturns200() throws Exception {
        // 1. Preparamos la respuesta del mock
        SentimentResponse mockResponse = new SentimentResponse(Prevision.POSITIVO, 0.99);
        when(sentimentService.predict(anyString())).thenReturn(mockResponse);

        var body = "{\"text\":\"Me encanta esta app, funciona bien\"}";
        mockMvc.perform(post("/sentiment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prevision").value("POSITIVO"))
                .andExpect(jsonPath("$.probabilidad").value(0.99));
    }

    /**
     * Caso: solicitud de estadísticas.
     * Verifica que el endpoint responda y tenga la estructura esperada.
     */
    @Test
        void whenGetStats_thenReturns200() throws Exception {
            when(statsService.getStats(anyInt()))
                    .thenReturn(new StatResponseDTO(
                            100,   // int
                            0L, 0L, 0L, 0L,  // 4 long
                            0.0, 0.0, 0.0    // 3 double
                    ));

            mockMvc.perform(get("/stats"))
                    .andExpect(status().isOk());
        }

    /**
     * Caso: Health Check General (Liveness).
     * Requisito Dev 4: /health siempre ok.
     */
    @Test
    void whenGetHealth_thenReturns200() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    /**
     * Caso: Health Check del Modelo (Readiness).
     * Requisito Dev 4: /health/model intenta ping.
     * En este test (con mocks), asumimos que responde OK.
     */
    @Test
    void whenGetModelHealth_thenReturns200() throws Exception {
        mockMvc.perform(get("/health/model"))
                .andExpect(status().isOk());
    }
}