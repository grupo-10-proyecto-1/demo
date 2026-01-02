package com.sentiment.demo.dto;


public record ErrorResponse(
        String error,
        String code
) {

}
