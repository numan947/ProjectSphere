package com.numan947.pmbackend.primary_packages.invitation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, String> {
    Optional<Invitation> findInvitationByProjectIdAndInvitationCode(String projectId, String invitationCode);
    Optional<Invitation> findInvitationByProjectIdAndUserEmail(String projectId, String userEmail);
}
