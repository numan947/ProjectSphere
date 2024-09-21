package com.numan947.pmbackend.exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;
import java.util.Set;
/**
 * ExceptionResponseDTO is a record that represents the structure of an exception response.
 * It includes fields for business error code, business error description, error message,
 * validation errors, and additional errors.
 *
 * Fields:
 * - businessErrorCode: The code representing the business error.
 * - businessErrorDescription: A brief description of the business error.
 * - error: The error message.
 * - validationErrors: A set of validation errors.
 * - errors: A map of additional errors.
 *
 * Annotations:
 * - @JsonInclude(JsonInclude.Include.NON_EMPTY): Ensures that only non-empty fields are included in the JSON response.
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record ExceptionResponseDTO(
        Integer businessErrorCode,
        String businessErrorDescription,
        String error,
        Set<String> validationErrors,
        Map<String, String> errors
) {
}