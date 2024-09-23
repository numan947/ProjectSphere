package com.numan947.pmbackend.primary_packages.invitation;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.email.EmailService;
import com.numan947.pmbackend.primary_packages.project.ProjectService;
import com.numan947.pmbackend.user.User;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class InvitationServiceImpl implements InvitationService{
    private final InvitationRepository invitationRepository;
    private final EmailService emailService;
    private final ProjectService projectService;

    // TODO: move these to application.properties
    private String invitationCodeCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private Integer invitationCodeLength = 12;
    private String invitationAcceptUrl = "http://localhost:9999/invitation/accept";


    @Override
    @Transactional
    public void createInvitation(String projectId, String userEmail, Authentication auth) throws MessagingException {
        User user = (User) auth.getPrincipal();
        Project project = projectService.findProjectByIdAndOwnerId(projectId, user.getId()).orElseThrow(
                () -> new EntityNotFoundException("Project not found")
        ); // check if the project exists and the user is the owner of the project at the same time

        //check if the user is already a member of the project
        if (project.getTeamMembers().stream().anyMatch(member -> member.getEmail().equals(userEmail))) {
            throw new OperationNotPermittedException("User is already a member of the project");
        }

        //check if the user is already invited to the project and the invitation is not expired
        Invitation existingInvitation = invitationRepository.findInvitationByProjectIdAndUserEmail(projectId, userEmail).orElse(null);
        if (existingInvitation != null) {
            //delete the existing invitation as we are going to create a new one ->
            //  1. if the user has an expired invitation
            //  2. if the user has an active invitation and the owner wants to send a new one
            // 3. if the user joined the project and then left the project and the owner wants to send a new invitation
            invitationRepository.delete(existingInvitation);
        }

        //create the invitation
        Invitation invitation = new Invitation();
        invitation.setInvitationCode(generateInvitationCode(invitationCodeLength));
        invitation.setUserEmail(userEmail);
        invitation.setProjectId(projectId);
        invitation.setExpiryDate(LocalDateTime.now().plusHours(24)); // 24 hours
        invitation.setProjectName(project.getName());
        invitationRepository.save(invitation);
        emailService.sendInvitationEmail(
                userEmail,
                invitation.getInvitationCode(),
                project.getName(),
                invitationAcceptUrl+"/"+projectId);
    }


    @Override
    @Transactional
    public void acceptInvitation(String projectId, String invitationCode, Authentication auth) {
        User user = (User) auth.getPrincipal();
        // validate invitation
        Invitation invitation = invitationRepository.findInvitationByProjectIdAndInvitationCode(projectId, invitationCode).orElseThrow(
                () -> new EntityNotFoundException("Invitation not found")
        ); // check if the invitation exists and the project id is correct

        if (!Objects.equals(invitation.getUserEmail(), user.getEmail())) {
            throw new OperationNotPermittedException("User email does not match the invitation email");
        } // check if the user email matches the invitation email
        if (invitation.getExpiryDate().isBefore(LocalDateTime.now()) || invitation.getJoinDate() != null) {
            throw new OperationNotPermittedException("Invitation has expired");
        } // check if the invitation has expired

        projectService.addTeamMemberToProject(projectId, user.getId()); // save the project
        invitation.setJoinDate(LocalDateTime.now());
        invitationRepository.save(invitation);// save the invitation
    }

    @Override
    public void removeMemberFromProject(String projectId, String userId, Authentication auth) {
        // 1. you can remove a member from a project only if you are the owner of the project
        // 2. if you are a member of the project -> you can remove yourself from the project
        // 3. exception-> owner cannot be removed

        User user = (User) auth.getPrincipal();
        Project project = projectService.findProjectById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Project not found")
        ); // check if the project exists and the user is the owner of the project at the same time
        log.info("User: "+user.getId());
        log.info("Owner: "+project.getOwner().getId());
        log.info("Member: "+userId);

        if (project.getOwner().getId().equals(userId)) {
            throw new OperationNotPermittedException("Owner cannot be removed from the project");
        } // check if the user is the owner of the project

        if (user.getId().equals(project.getOwner().getId())){
            // owner is removing a member
            projectService.removeTeamMemberFromProject(projectId, userId);
        }
        else{
            // member is removing himself
            // check if the user is part of the project
            if(projectService.isUserPartOfProject(user.getId(), projectId) && user.getId().equals(userId)){
                projectService.removeTeamMemberFromProject(projectId, userId); // remove the member
            }
            else{
                throw new OperationNotPermittedException("Invalid operation");
            }
        }
    }

    private String generateInvitationCode(Integer codeLength) {
        StringBuilder code = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            code.append(invitationCodeCharacters.charAt(random.nextInt(invitationCodeCharacters.length())));
        }
        return code.toString();
    }


}
