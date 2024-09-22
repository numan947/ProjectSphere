package com.numan947.pmbackend.primary_packages.invitation;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.email.EmailService;
import com.numan947.pmbackend.primary_packages.project.ProjectService;
import com.numan947.pmbackend.user.User;
import com.numan947.pmbackend.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService{
    private final InvitationRepository invitationRepository;
    private final EmailService emailService;
    private final ProjectService projectService;
    private final UserService userService;

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
    public void deleteInvitation(String projectId, String invitationCode, Authentication auth) {
        User user = (User) auth.getPrincipal();
        Project project = projectService.findProjectByIdAndOwnerId(projectId, user.getId()).orElseThrow(
                () -> new EntityNotFoundException("Project not found")
        ); // check if the project exists and the user is the owner of the project at the same time'
        Invitation invitation = invitationRepository.findInvitationByProjectIdAndInvitationCode(projectId, invitationCode).orElseThrow(
                () -> new EntityNotFoundException("Invitation not found")
        ); // check if the invitation exists and the project id is correct
        invitationRepository.delete(invitation);
    }


    @Override
    @Transactional
    public void acceptInvitation(String projectId, String invitationCode, Authentication auth) {
        User user = (User) auth.getPrincipal();

        // validate project and user
        Project project = projectService.findProjectById(projectId).orElseThrow(
                () -> new EntityNotFoundException("Project not found")
        ); // check if the project exists
        if (project.getTeamMembers().stream().anyMatch(member -> member.getId().equals(user.getId()))) {
            throw new OperationNotPermittedException("User is already a member of the project");
        } // check if the user is already a member of the project -> this also checks if the user is the owner of the project because the owner is also a member

        // validate invitation
        Invitation invitation = invitationRepository.findInvitationByProjectIdAndInvitationCode(projectId, invitationCode).orElseThrow(
                () -> new EntityNotFoundException("Invitation not found")
        ); // check if the invitation exists and the project id is correct

        if (!Objects.equals(invitation.getUserEmail(), user.getEmail())) {
            throw new OperationNotPermittedException("User email does not match the invitation email");
        } // check if the user email matches the invitation email
        if (invitation.getExpiryDate().isBefore(LocalDateTime.now()) || invitation.getAcceptedDate() != null) {
            throw new OperationNotPermittedException("Invitation has expired");
        } // check if the invitation has expired


        project.getTeamMembers().add(user); // add the user to the project
//        project.getChat().getMembers().add(user); // add the user to the chat
        userService.updateProjectSize(user.getId(), true); // update the project size of the user
        projectService.updateProject(project); // save the project
        invitation.setAcceptedDate(LocalDateTime.now());
        invitationRepository.save(invitation);// save the invitation
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
