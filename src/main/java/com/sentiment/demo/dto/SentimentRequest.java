package com.sentiment.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SentimentRequest(
        @JsonProperty("text")
        @NotBlank(message = "El texto no puede estar vacío")
        @Size(min = 5, max = 2000, message = "El texto debe tener al entre 5 y 2000 caracteres")
        String text
                               )
{
    
}