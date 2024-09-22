package com.numan947.pmbackend.primary_packages.invitation;

import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;

public interface InvitationService {
    void createInvitation(String projectId, String userEmail, Authentication auth) throws MessagingException;

    void acceptInvitation(String projectId, String invitationCode, Authentication auth);

    void deleteInvitation(String projectId, String invitationCode, Authentication auth);
}
