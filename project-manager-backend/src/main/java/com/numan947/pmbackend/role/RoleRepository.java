package com.numan947.pmbackend.role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * RoleRepository is a Spring Data JPA repository for managing Role entities.
 * It provides methods for performing CRUD operations and custom queries on Role entities.
 *
 * Methods:
 * - findByName: Finds a Role entity by its name.
 *
 * Annotations:
 * - @Repository: Indicates that the interface is a Spring Data repository.
 */
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}