package com.numan947.pmbackend.security.auth.dto;

public record LoginResponseDTO(
        String id,
        String email,
        String token,
        String fullName

){
}
