package com.numan947.pmbackend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.primary_packages.issue.Issue;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
 * User is an entity that represents a user in the system.
 * It extends BaseEntity and implements UserDetails and Principal interfaces for Spring Security integration.
 *
 * Fields:
 * - firstName: The first name of the user.
 * - lastName: The last name of the user.
 * - email: The unique email of the user.
 * - password: The password of the user.
 * - accountLocked: Indicates if the account is locked.
 * - accountEnabled: Indicates if the account is enabled.
 * - roles: A list of roles associated with the user.
 * - numberOfProjects: The number of projects the user has.
 *
 * Annotations:
 * - @Entity: Specifies that the class is an entity and is mapped to a database table.
 * - @Table(name = "_users"): Specifies the name of the database table to be used for mapping.
 * - @Column(unique = true): Specifies that the email column should have unique values.
 * - @ManyToMany(fetch = FetchType.EAGER): Specifies a many-to-many relationship with the Role entity.
 * - @Getter: Lombok annotation to generate getter methods for all fields.
 * - @Setter: Lombok annotation to generate setter methods for all fields.
 * - @AllArgsConstructor: Lombok annotation to generate a constructor with all fields as parameters.
 * - @NoArgsConstructor: Lombok annotation to generate a no-argument constructor.
 * - @Builder: Lombok annotation to implement the builder pattern for the class.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_users")
@Entity
public class User extends BaseEntity implements UserDetails, Principal {

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private String password;

    private boolean accountLocked;
    private boolean accountEnabled;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    //  Project related fields
    private Long numberOfProjects;

    @JsonIgnore
    @OneToMany(mappedBy = "assignedUser", cascade = CascadeType.ALL)
    private List<Issue>assignedIssues;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Project>projects;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Chat> chats;



    @Override
    public String getName() {
        return email; // should return unique identifier
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // should return unique identifier
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // ACCOUNT EXPIRATION IS NOT IMPLEMENTED
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // CREDENTIALS EXPIRATION IS NOT IMPLEMENTED
    }

    @Override
    public boolean isEnabled() {
        return accountEnabled;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}