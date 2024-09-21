package com.numan947.pmbackend.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ExceptionResponseDTO(
        Integer businessErrorCode,
        String businessErrorDescription,
        String error,
        Set<String> validationErrors,
        Map<String, String> errors
) {
}