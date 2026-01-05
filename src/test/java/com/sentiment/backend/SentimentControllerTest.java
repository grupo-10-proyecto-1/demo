<<<<<<< HEAD:SentimentControllerMockMvcTest.java
package com.sentiment.demo.controller;
=======
package com.sentiment.backend;
>>>>>>> main:src/test/java/com/sentiment/backend/SentimentControllerTest.java

import com.sentiment.demo.dto.SentimentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;

<<<<<<< HEAD:SentimentControllerMockMvcTest.java
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
=======
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
>>>>>>> main:src/test/java/com/sentiment/backend/SentimentControllerTest.java
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SentimentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean // Nos permite "espiar" el bean real y forzar errores en métodos específicos
    private SentimentController sentimentController;

    @Test
<<<<<<< HEAD:SentimentControllerMockMvcTest.java
    public void testSentimentAnalysis_Success() throws Exception {
        // Caso feliz: Texto válido
        String jsonRequest = "{\"text\": \"Este es un mensaje de prueba positivo\"}";

        mockMvc.perform(post("/sentiment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.prevision").exists());
    }

    @Test
    public void testSentimentAnalysis_BadRequest_Empty() throws Exception {
        // Caso error: Texto vacío
=======
    void whenValidText_thenReturns200() throws Exception {
        // Simulamos un request válido con texto
        String jsonRequest = "{\"text\": \"Este es un gran proyecto de hackathon\"}";

        mockMvc.perform(post("/sentiment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest))
                .andExpect(status().isOk());
    }

    @Test
    void whenEmptyText_thenReturns400() throws Exception {
        // Simulamos un request inválido (texto vacío) para probar validaciones
>>>>>>> main:src/test/java/com/sentiment/backend/SentimentControllerTest.java
        String jsonRequest = "{\"text\": \"\"}";

        mockMvc.perform(post("/sentiment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
<<<<<<< HEAD:SentimentControllerMockMvcTest.java
    public void testSentimentAnalysis_BadRequest_TooShort() throws Exception {
        // Caso error: Texto muy corto (< 5 caracteres)
        // Validamos que @Size(min=5) funcione
        String jsonRequest = "{\"text\": \"Hola\"}";

        mockMvc.perform(post("/sentiment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    public void testSentimentAnalysis_BadRequest_TooLong() throws Exception {
        // Caso error: Texto demasiado largo (> 2000 caracteres)
        // Generamos un string de 2001 caracteres
        String longText = "a".repeat(2001);
        String jsonRequest = "{\"text\": \"" + longText + "\"}";

        mockMvc.perform(post("/sentiment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }

    @Test
    public void testSentimentAnalysis_ServiceUnavailable() throws Exception {
        // Caso error: Simular que el servicio de Python no responde (Timeout/Connection Refused)
        // Forzamos al controlador a lanzar la excepción que lanzaría RestTemplate
        doThrow(new ResourceAccessException("Connection timed out"))
                .when(sentimentController).analizar(any(SentimentRequest.class));

        String jsonRequest = "{\"text\": \"Texto válido pero el servicio falla\"}";

        mockMvc.perform(post("/sentiment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isServiceUnavailable()) // Esperamos 503
                .andExpect(jsonPath("$.error").value("Modelo no disponible"))
                .andExpect(jsonPath("$.code").value("MODEL_UNAVAILABLE"));
=======
    void healthCheck_shouldReturnOk() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
>>>>>>> main:src/test/java/com/sentiment/backend/SentimentControllerTest.java
    }
}