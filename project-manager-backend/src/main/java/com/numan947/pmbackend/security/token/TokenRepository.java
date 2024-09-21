package com.numan947.pmbackend.security.token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * TokenRepository is a Spring Data JPA repository for managing Token entities.
 * It provides methods for performing CRUD operations and custom queries on Token entities.
 *
 * Methods:
 * - findByToken: Finds a Token entity by its token string.
 *
 * Annotations:
 * - @Repository: Indicates that the interface is a Spring Data repository.
 */
public interface TokenRepository extends JpaRepository<Token, String> {
    Optional<Token> findByTokenAndType(String token, String type);
}