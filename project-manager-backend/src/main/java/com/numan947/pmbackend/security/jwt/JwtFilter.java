package com.numan947.pmbackend.security.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JwtFilter is a custom filter that extends OncePerRequestFilter to handle JWT validation for incoming HTTP requests.
 * It checks the Authorization header for a JWT token, validates it, and sets the authentication in the SecurityContextHolder if the token is valid.
 *
 * Annotations:
 * - @Component: Indicates that this class is a Spring component.
 * - @RequiredArgsConstructor: Lombok annotation to generate a constructor with required arguments.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    @Value("${server.servlet.context-path}")
    private String contextPath;
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Filters incoming requests to validate JWT tokens.
     *
     * @param request the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @param filterChain the FilterChain object.
     * @throws ServletException if an error occurs during the filtering process.
     * @throws IOException if an I/O error occurs during the filtering process.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        System.out.println("JwtFilter.doFilterInternal");
        // Skip JWT validation for auth endpoints (login, register)
        if (request.getRequestURI().startsWith(contextPath + "/api/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        // Validate JWT
        // Get the Authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            // Extract the token and validate it
            String jwtToken = authHeader.substring(7);
            Claims allClaimsFromToken = jwtService.extractAllClaims(jwtToken);
            String email = jwtService.extractClaim(allClaimsFromToken, Claims::getSubject);
            // Only authenticate if the user is not already authenticated
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Make sure the user exists
                var userDetails = userDetailsService.loadUserByUsername(email);
                if (userDetails != null && jwtService.isTokenValid(jwtToken, userDetails)) {
                    var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}