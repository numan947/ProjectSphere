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
 * - generateToken(Map<String, Object> extraClaims, UserDetails userDetails): Generates a JWT token for the given user details.
 * - buildToken(Map<String, Object> extraClaims, UserDetails userDetails): Builds a JWT token with extra claims for the given user details.
 * - getSignatureKey(): Reads the secret key from the application properties and creates a SecretKey object.
 * - isTokenValid(String token, UserDetails userDetails): Validates the token and extracts the email from it.
 * - isTokenExpired(Date expiration): Checks if the token is expired.
 * - extractClaim(Claims claims, Function<Claims, T> claimResolver): Extracts a claim from the extracted claims.
 * - extractAllClaims(String token): Extracts all the claims from the token.
 */
@Service
public class JWTService {
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.secret}")
    private String jwtSecret;

    /**
     * Generates a JWT token for the user with extra claims.
     *
     * @param extraClaims Additional claims to be included in the token.
     * @param userDetails The user details for which the token is generated.
     * @return The generated JWT token.
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return buildToken(extraClaims, userDetails);
    }

    /**
     * Builds a JWT token with extra claims for the given user details.
     *
     * @param extraClaims Additional claims to be included in the token.
     * @param userDetails The user details for which the token is generated.
     * @return The built JWT token.
     */
    private String buildToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return Jwts.builder()
                .claims(extraClaims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("authorities", authorities)
                .signWith(getSignatureKey())
                .compact();
    }

    /**
     * Reads the secret key from the application properties and creates a SecretKey object.
     *
     * @return The created SecretKey object.
     */
    private SecretKey getSignatureKey() {
        byte[] secretBytes = jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(secretBytes);
    }

    /**
     * Validates the token and extracts the email from it.
     *
     * @param token The JWT token to be validated.
     * @param userDetails The user details to be validated against.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        Claims claims = extractAllClaims(token);
        String email = extractClaim(claims, Claims::getSubject);
        Date expiration = extractClaim(claims, Claims::getExpiration);
        return email.equals(userDetails.getUsername()) && !isTokenExpired(expiration);
    }

    /**
     * Checks if the token is expired.
     *
     * @param expiration The expiration date of the token.
     * @return True if the token is expired, false otherwise.
     */
    private boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }

    /**
     * Extracts a claim from the extracted claims.
     *
     * @param claims The extracted claims.
     * @param claimResolver The function to resolve the claim.
     * @param <T> The type of the claim.
     * @return The extracted claim.
     */
    public <T> T extractClaim(Claims claims, Function<Claims, T> claimResolver) {
        return claimResolver.apply(claims);
    }

    /**
     * Extracts all the claims from the token.
     *
     * @param token The JWT token from which the claims are extracted.
     * @return The extracted claims.
     */
    public Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSignatureKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}