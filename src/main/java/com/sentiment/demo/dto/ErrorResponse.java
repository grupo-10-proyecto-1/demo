package com.sentiment.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorResponse(
        String error,
        String code
) {

}
