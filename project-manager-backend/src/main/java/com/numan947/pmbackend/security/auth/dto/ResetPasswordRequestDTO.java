package com.numan947.pmbackend.security.auth.dto;

public record ResetPasswordRequestDTO(
        String password,
        String resetcode
) {
}
