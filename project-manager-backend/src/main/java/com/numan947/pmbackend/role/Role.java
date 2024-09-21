package com.numan947.pmbackend.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

/**
 * Role is an entity that represents a user role in the system.
 * It extends BaseEntity and includes fields for the role name, description, and associated users.
 *
 * Fields:
 * - name: The unique name of the role.
 * - description: A brief description of the role.
 * - users: A list of users associated with this role.
 *
 * Annotations:
 * - @Entity: Specifies that the class is an entity and is mapped to a database table.
 * - @Table(name="_roles"): Specifies the name of the database table to be used for mapping.
 * - @Column(unique = true): Specifies that the name column should have unique values.
 * - @ManyToMany(mappedBy = "roles"): Specifies a many-to-many relationship with the User entity.
 * - @JsonIgnore: Ensures that the users field is ignored during JSON serialization.
 * - @Getter: Lombok annotation to generate getter methods for all fields.
 * - @Setter: Lombok annotation to generate setter methods for all fields.
 * - @AllArgsConstructor: Lombok annotation to generate a constructor with all fields as parameters.
 * - @NoArgsConstructor: Lombok annotation to generate a no-argument constructor.
 * - @Builder: Lombok annotation to implement the builder pattern for the class.
 */
@Entity
@Table(name="_roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role extends BaseEntity {
    @Column(unique = true)
    private String name;
    private String description;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnore
    private List<User> users;
}