package com.numan947.pmbackend.security.auth;


import com.numan947.pmbackend.security.auth.dto.LoginRequestDTO;
import com.numan947.pmbackend.security.auth.dto.LoginResponseDTO;
import com.numan947.pmbackend.security.auth.dto.RegistrationRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegistrationRequestDTO registrationRequestDTO) throws MessagingException {
        authenticationService.registerUser(registrationRequestDTO);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam("activation-code") String activationCode) {
        authenticationService.activateAccount(activationCode);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/resend-activation")
    public ResponseEntity<?> resendActivation(@RequestParam("email") String email) throws MessagingException {
        authenticationService.resendActivation(email);
        return ResponseEntity.accepted().build();
    }

}
