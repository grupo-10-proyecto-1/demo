package com.sentiment.demo.exception;


import com.sentiment.demo.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestControllerAdvice
public class GlobalExceptionHandler {

    // Si el backend recibe algo raro o se cae una parte, devolver un JSON amigable
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error interno del servidor"));
    }

    //todavía NO existe el RestTemplate ni el error específico de Python. 
    //Cuando Dev 1 lo integre,   agregas un handler extra más específico aquí.
    //Cuando integren RestTemplate, agrega este método dentro del mismo handler:
    //Esto te deja perfecto para cuando Dev1 conecte FastAPI.
    /*
     * import org.springframework.web.client.ResourceAccessException;

       

        @ExceptionHandler(ResourceAccessException.class)
        public ResponseEntity<ErrorResponse> handlePythonDown() {
            return ResponseEntity
                    .status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new ErrorResponse("Modelo no disponible"));
        }
     */
}
