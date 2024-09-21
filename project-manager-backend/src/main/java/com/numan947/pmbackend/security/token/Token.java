package com.numan947.pmbackend.security.token;

import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Token is an entity that represents a token used for account verification, activation, and password reset.
 * It extends BaseEntity and is associated with a User entity.
 *
 * Fields:
 * - token: The activation code for verification and activation of the account.
 * - creationTime: The date and time when the token was created.
 * - expirationTime: The date and time when the token will expire.
 * - validationTime: The date and time when the token was validated.
 * - user: The user associated with the token.
 *
 * Annotations:
 * - @Entity: Specifies that the class is an entity and is mapped to a database table.
 * - @Table(name = "_tokens"): Specifies the name of the database table to be used for mapping.
 * - @ManyToOne: Specifies a many-to-one relationship with the User entity.
 * - @JoinColumn: Specifies the foreign key column for the user relationship.
 * - @Getter: Lombok annotation to generate getter methods for all fields.
 * - @Setter: Lombok annotation to generate setter methods for all fields.
 * - @AllArgsConstructor: Lombok annotation to generate a constructor with all fields as parameters.
 * - @NoArgsConstructor: Lombok annotation to generate a no-argument constructor.
 * - @Builder: Lombok annotation to implement the builder pattern for the class.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "_tokens")
public class Token extends BaseEntity {
    private String token; // this is not jwt token, this is just activation code for verification and activation of account, also for password reset
    private LocalDateTime creationTime; // date of creation
    private LocalDateTime expirationTime; // date of expiration
    private LocalDateTime validationTime; // date of validation
    private String type; // type of token, can be verification, activation, or password reset

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}