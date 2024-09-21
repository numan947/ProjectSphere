package com.numan947.pmbackend.user;

import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

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
    private List<Role>roles;


    // number of projects user has
    private Long numberOfProjects;



    @Override
    public String getName() {
        return email; // should return unique identifier
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(role->new SimpleGrantedAuthority(role.getName())).toList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        // should return unique identifier
        return email;
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

    public String getFullName(){
        return firstName + " " + lastName;
    }
}
