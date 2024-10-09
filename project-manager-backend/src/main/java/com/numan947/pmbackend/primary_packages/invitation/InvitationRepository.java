package com.numan947.pmbackend.primary_packages.invitation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation, String> {
    Optional<Invitation> findInvitationByProjectIdAndInvitationCode(String projectId, String invitationCode);
    Optional<Invitation> findInvitationByProjectIdAndUserEmail(String projectId, String userEmail);

    @Query("SELECT i FROM Invitation i WHERE i.userEmail = :userEmail AND i.joinDate IS NULL")
    List<Invitation> findAllPendingInvitationsByUserEmail(String userEmail);
}
