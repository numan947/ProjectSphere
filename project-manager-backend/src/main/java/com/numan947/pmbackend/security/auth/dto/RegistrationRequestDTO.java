package com.numan947.pmbackend.security.auth.dto;

import jakarta.validation.constraints.*;

public record RegistrationRequestDTO(
        @NotNull(message = "Email cannot be null")
        @NotEmpty(message = "Email cannot be empty")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be valid")
        String email,
        @NotNull(message = "Password cannot be null")
        @NotEmpty(message = "Password cannot be empty")
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 6, message = "Password must be at least 6 characters long")
        String password,

        @NotNull(message = "First name cannot be null")
        @NotEmpty(message = "First name cannot be empty")
        @NotBlank(message = "First name cannot be blank")
        String fname,

        @NotNull(message = "Last name cannot be null")
        @NotEmpty(message = "Last name cannot be empty")
        @NotBlank(message = "Last name cannot be blank")
        String lname
) {
}
