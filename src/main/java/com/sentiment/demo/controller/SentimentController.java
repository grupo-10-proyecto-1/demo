package com.sentiment.demo.controller;

import com.sentiment.demo.dto.ErrorResponse;
import com.sentiment.demo.dto.SentimentResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/sentiment") // La URL será: http://localhost:8080/sentiment
public class SentimentController {

    private static final int MIN_LEN = 3;

    @PostMapping
    public ResponseEntity<?> analizar(@RequestBody Map<String, String> body) {
        // 1. Extraemos el texto del JSON que envió el usuario
        String texto = body.get("text");

        // 2. Validación: Si no hay texto, devolvemos error (400 Bad Request)
        if (texto == null || texto.trim().isEmpty()) {
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("Error: El campo 'text' es obligatorio."));
        }

        if (texto.trim().length() < MIN_LEN) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponse("El campo 'text' debe tener al menos " + MIN_LEN + " caracteres."));
        }
        
        // 3. Por ahora, devolvemos una respuesta "ficticia" (Mock) 
        // para confirmar que el Backend funciona.
        //return ResponseEntity.ok(new SentimentResponse("Positivo", 0.99));

        // simulamos un analisis positivo de probabalidad
        SentimentResponse respuestaExitosa = new SentimentResponse("positivo", 0.95);

        return ResponseEntity.ok(respuestaExitosa);
    }
}