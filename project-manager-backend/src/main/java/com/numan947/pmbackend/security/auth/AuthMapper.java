package com.numan947.pmbackend.security.auth;

import com.numan947.pmbackend.role.Role;
import com.numan947.pmbackend.security.auth.dto.RegistrationRequestDTO;
import com.numan947.pmbackend.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthMapper {
    private final PasswordEncoder passwordEncoder;
    public User toUserForRegistration(@Valid RegistrationRequestDTO registrationRequestDTO, Role userRole) {
        return User.builder()
                .email(registrationRequestDTO.email())
                .password(passwordEncoder.encode(registrationRequestDTO.password()))
                .roles(List.of(userRole))
                .accountEnabled(false)
                .accountLocked(false)
                .firstName(registrationRequestDTO.fname())
                .lastName(registrationRequestDTO.lname())
                .numberOfProjects(0L)
                .build();
    }

}
