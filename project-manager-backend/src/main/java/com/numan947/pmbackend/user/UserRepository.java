package com.numan947.pmbackend.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * UserRepository is a Spring Data JPA repository for managing User entities.
 * It provides methods for performing CRUD operations and custom queries on User entities.
 *
 * Methods:
 * - findByEmail: Finds a User entity by its email.
 *
 * Annotations:
 * - @Repository: Indicates that the interface is a Spring Data repository.
 */
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);
}