package com.numan947.pmbackend.primary_packages.invitation;

import com.numan947.pmbackend.primary_packages.invitation.dto.InvitationResponse;
import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface InvitationService {
    void createInvitation(String projectId, List<String> emails, Authentication auth) throws MessagingException;

    void acceptInvitation(String invitationId, Authentication auth);

    void removeMemberFromProject(String projectId, String userId, Authentication auth);

    List<InvitationResponse> getPendingInvites(Authentication auth);

    void rejectInvitation(String invitationId, Authentication auth);
}
