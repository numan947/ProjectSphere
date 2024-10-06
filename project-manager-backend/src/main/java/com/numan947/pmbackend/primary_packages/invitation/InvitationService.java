package com.numan947.pmbackend.primary_packages.invitation;

import jakarta.mail.MessagingException;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface InvitationService {
    void createInvitation(String projectId, List<String> emails, Authentication auth) throws MessagingException;

    void acceptInvitation(String projectId, String invitationCode, Authentication auth);

    void removeMemberFromProject(String projectId, String userId, Authentication auth);
}
