package com.numan947.pmbackend.security.auth;

import com.numan947.pmbackend.role.RoleRepository;
import com.numan947.pmbackend.security.auth.dto.LoginRequestDTO;
import com.numan947.pmbackend.security.auth.dto.LoginResponseDTO;
import com.numan947.pmbackend.security.auth.dto.RegistrationRequestDTO;
import com.numan947.pmbackend.security.email.EmailService;
import com.numan947.pmbackend.security.jwt.JWTService;
import com.numan947.pmbackend.security.token.Token;
import com.numan947.pmbackend.security.token.TokenRepository;
import com.numan947.pmbackend.user.User;
import com.numan947.pmbackend.user.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    private final AuthMapper authMapper;
    private final EmailService emailService;


    @Value("${application.security.mailing.frontend.activation-url:http://localhost:8080/auth/activate}")
    private String activationUrl;
    @Value("${application.security.mailing.frontend.activation-code-length:6}")
    private Integer activationCodeLength;
    @Value("${application.security.mailing.frontend.activation-code-expiration:15}")
    private Integer activationCodeExpirationTime = 15;
    @Value("${application.security.mailing.frontend.activation-code-characters:0123456789}")
    private String activationCodeCharacters;
    @Value("${application.security.mailing.frontend.activation-code-subject:Activate your account}")
    private String activationCodeSubject;


    @Transactional
    public void registerUser(@Valid RegistrationRequestDTO registrationRequestDTO) throws MessagingException {
        var userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() ->  new RuntimeException("ROLE_USER not found in role repository")); // TODO: ADD CUSTOM EXCEPTION
        // check if user already exists
        if (userRepository.findByEmail(registrationRequestDTO.email()).isPresent()) {
            throw new RuntimeException("User with email " + registrationRequestDTO.email() + " already exists"); // TODO: ADD CUSTOM EXCEPTION
        }
        // create user
        var user = authMapper.toUserForRegistration(registrationRequestDTO, userRole);
        userRepository.save(user);

        // create and save activation code
        var activationCode = generateAndSaveActivationToken(user);

        // send activation email
        emailService.sendAccountActivationEmail(
                user.getEmail(),
                user.getFullName(),
                activationUrl,
                activationCode,
                activationCodeSubject
        );
    }




    // private methods
    private String generateAndSaveActivationToken(User user) {
        String generatedToken = generateActivationCode(activationCodeLength);
        var token = Token.builder()
                .token(generatedToken)
                .creationTime(LocalDateTime.now())
                .expirationTime(LocalDateTime.now().plusMinutes(activationCodeExpirationTime))
                .user(user)
                .build();
        tokenRepository.save(token);
        return generatedToken;
    }
    private String generateActivationCode(Integer codeLength) {
        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            code.append(activationCodeCharacters.charAt(random.nextInt(activationCodeCharacters.length())));
        }
        return code.toString();
    }


    public LoginResponseDTO login(@Valid LoginRequestDTO request) {
        var auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var claims = new HashMap<String, Object>();
        var user = (User) auth.getPrincipal();
        claims.put("fullName", user.getFullName());
        var token = jwtService.generateToken(claims, user);
        return new LoginResponseDTO(
                user.getId(),
                user.getEmail(),
                token,
                user.getFullName()
        );
    }

    @Transactional
    public void activateAccount(String activationCode) {
        var token = tokenRepository.findByToken(activationCode).orElseThrow(() -> new RuntimeException("Token not found")); // TODO: ADD CUSTOM EXCEPTION
        if (token.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired"); // TODO: ADD CUSTOM EXCEPTION
        }
        var user = userRepository.findById(token.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found")); // TODO: ADD CUSTOM EXCEPTION
        user.setAccountEnabled(true);
        userRepository.save(user);
        token.setValidationTime(LocalDateTime.now());
        tokenRepository.save(token);
    }

    public void resendActivation(String email) throws MessagingException {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found")); // TODO: ADD CUSTOM EXCEPTION
        if (user.isAccountEnabled()) {
            throw new RuntimeException("Account already activated"); // TODO: ADD CUSTOM EXCEPTION
        }

        // TODO: May be a good idea to expire old tokens before creating a new one
        // create and save activation code
        var activationCode = generateAndSaveActivationToken(user);
        // send activation email
        emailService.sendAccountActivationEmail(
                user.getEmail(),
                user.getFullName(),
                activationUrl,
                activationCode,
                activationCodeSubject
        );
    }
}
