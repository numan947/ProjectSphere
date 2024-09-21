package com.numan947.pmbackend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
/**
 * JWTService is a service class that handles the generation and validation of JWT tokens.
 *
 * Fields:
 * - jwtExpiration: The expiration time of the JWT token, injected from application properties.
 * - jwtSecret: The secret key used to sign the JWT token, injected from application properties.
 *
 * Methods:
 * - generateToken(UserDetails userDetails): Generates a JWT token for the given user details.
 * - buildToken(HashMap<String, Object> extraClaims, UserDetails userDetails): Builds a JWT token with extra claims for the given user details.
 * - getSignatureKey(): Reads the secret key from the application properties and creates a SecretKey object.
 * - isTokenValid(String token, UserDetails userDetails): Validates the token and extracts the email from it.
 * - isTokenExpired(Date expiration): Checks if the token is expired.
 * - extractClaim(Claims claims, Function<Claims, T> claimResolver): Extracts a claim from the extracted claims.
 * - extractAllClaims(String token): Extracts all the claims from the token.
 *
 * Method Details:
 * - generateToken(UserDetails userDetails):
 *   Generates a JWT token for the given user details.
 *   Parameters:
 *   - userDetails: The user details for which the token is generated.
 *   Returns: The generated JWT token.
 *
 * - buildToken(HashMap<String, Object> extraClaims, UserDetails userDetails):
 *   Builds a JWT token with extra claims for the given user details.
 *   Parameters:
 *   - extraClaims: Additional claims to be included in the token.
 *   - userDetails: The user details for which the token is generated.
 *   Returns: The built JWT token.
 *
 * - getSignatureKey():
 *   Reads the secret key from the application properties and creates a SecretKey object.
 *   Returns: The created SecretKey object.
 *
 * - isTokenValid(String token, UserDetails userDetails):
 *   Validates the token and extracts the email from it.
 *   Parameters:
 *   - token: The JWT token to be validated.
 *   - userDetails: The user details to be validated against.
 *   Returns: True if the token is valid, false otherwise.
 *
 * - isTokenExpired(Date expiration):
 *   Checks if the token is expired.
 *   Parameters:
 *   - expiration: The expiration date of the token.
 *   Returns: True if the token is expired, false otherwise.
 *
 * - extractClaim(Claims claims, Function<Claims, T> claimResolver):
 *   Extracts a claim from the extracted claims.
 *   Parameters:
 *   - claims: The extracted claims.
 *   - claimResolver: The function to resolve the claim.
 *   Returns: The extracted claim.
 *
 * - extractAllClaims(String token):
 *   Extracts all the claims from the token.
 *   Parameters:
 *   - token: The JWT token from which the claims are extracted.
 *   Returns: The extracted claims.
 */


@Service
public class JWTService {
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    // Generate a JWT token for the user --> FLOW: UserDetails -> JWT Token
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Build the token with the user details
        return buildToken(extraClaims, userDetails);
    }
    // Generate a JWT token for the user with extra claims --> FLOW: UserDetails -> JWT Token
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        // Get the user authorities
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        // Build the token
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("authorities", authorities)
                .signWith(getSignatureKey())
                .compact();
    }
    // Read the secret key from the application.yml file and create a SecretKey object --> FLOW: UserDetails -> JWT Token
    private SecretKey getSignatureKey() {
        byte[] secretBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(secretBytes);
    }


    // Validate the token and extract the email from it --> FLOW: JWT Token -> Email
    public boolean isTokenValid(String token, UserDetails userDetails) {
        // Extract all of the claims from the token
        Claims claims = extractAllClaims(token);
        // Extract the email from the token
        String email = extractClaim(claims, Claims::getSubject);
        // Extract the expiration date from the token
        Date expiration = extractClaim(claims, Claims::getExpiration);

        // Check if the email is the same as the one in the UserDetails
        return email.equals(userDetails.getUsername()) && !isTokenExpired(expiration);
    }

    private boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
    // Extract a claim from the extracted claims
    public <T> T extractClaim(Claims claims, Function<Claims, T> claimResolver) {
        return claimResolver.apply(claims);
    }
    // Extract all the claims from the token
    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignatureKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
