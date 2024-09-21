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

/**
 * AuthenticationController is a REST controller that handles user authentication-related endpoints.
 *
 * Annotations:
 * - @RestController: Indicates that this class is a REST controller.
 * - @RequestMapping: Maps HTTP requests to handler methods of MVC and REST controllers.
 * - @Tag: Adds a tag to the Swagger documentation.
 * - @RequiredArgsConstructor: Lombok annotation to generate a constructor with required arguments.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    /**
     * Registers a new user.
     *
     * @param registrationRequestDTO The registration request data transfer object.
     * @return A ResponseEntity indicating the result of the registration.
     * @throws MessagingException if an error occurs while sending the activation email.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(
            @Valid @RequestBody RegistrationRequestDTO registrationRequestDTO) throws MessagingException {
        authenticationService.registerUser(registrationRequestDTO);
        return ResponseEntity.accepted().build();
    }

    /**
     * Logs in a user.
     *
     * @param request The login request data transfer object.
     * @return A ResponseEntity containing the login response data transfer object.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    /**
     * Activates a user account.
     *
     * @param activationCode The activation code.
     * @return A ResponseEntity indicating the result of the activation.
     */
    @PostMapping("/activate")
    public ResponseEntity<?> activate(@RequestParam("activation-code") String activationCode) {
        authenticationService.activateAccount(activationCode);
        return ResponseEntity.accepted().build();
    }

    /**
     * Resends the activation email.
     *
     * @param email The email address to which the activation email is sent.
     * @return A ResponseEntity indicating the result of the resend operation.
     * @throws MessagingException if an error occurs while sending the activation email.
     */
    @GetMapping("/resend-activation")
    public ResponseEntity<?> resendActivation(@RequestParam("email") String email) throws MessagingException {
        authenticationService.resendActivation(email);
        return ResponseEntity.accepted().build();
    }
}