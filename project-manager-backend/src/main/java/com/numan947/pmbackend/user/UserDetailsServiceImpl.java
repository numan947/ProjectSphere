package com.numan947.pmbackend.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * UserDetailsServiceImpl is a service that implements UserDetailsService to provide user details for authentication.
 *
 * Annotations:
 * - @Service: Indicates that this class is a Spring service.
 * - @RequiredArgsConstructor: Lombok annotation to generate a constructor with required arguments.
 */
@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    /**
     * Loads the user by email.
     *
     * @param email the email of the user.
     * @return UserDetails of the user.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}