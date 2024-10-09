package com.numan947.pmbackend.primary_packages.invitation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public record InvitationResponse(
        String invitationId,
        String invitationCode,
        String userEmail,
        String projectId,
        String projectName,
        LocalDateTime expiryDate,
        LocalDateTime joinDate,
        LocalDateTime leaveDate
) {
}
