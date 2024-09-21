package com.numan947.pmbackend.security.token;

import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

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

    @ManyToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
}
