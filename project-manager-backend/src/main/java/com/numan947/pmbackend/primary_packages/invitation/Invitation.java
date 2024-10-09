package com.numan947.pmbackend.primary_packages.invitation;

import com.numan947.pmbackend.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "_invitations")
public class Invitation extends BaseEntity {
    private String invitationCode; // TODO: I think this is redundant, as we can use the id as the invitation code.
    private String userEmail;
    private String projectId;
    private String projectName;
    private LocalDateTime expiryDate;
    private LocalDateTime joinDate;
    private LocalDateTime leaveDate;
}
