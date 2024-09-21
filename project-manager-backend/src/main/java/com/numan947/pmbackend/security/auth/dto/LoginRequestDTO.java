package com.numan947.pmbackend.security.auth.dto;

import jakarta.validation.constraints.*;

public record LoginRequestDTO(
        @NotBlank(message = "Email cannot be blank")
        @NotNull(message = "Email cannot be null")
        @NotEmpty(message = "Email cannot be empty")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @NotNull(message = "Password cannot be null")
        @NotEmpty(message = "Password cannot be empty")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password
) {
}
