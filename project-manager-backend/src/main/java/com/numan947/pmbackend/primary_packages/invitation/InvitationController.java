package com.numan947.pmbackend.primary_packages.invitation;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invitation")
@Tag(name = "Invitation", description = "Endpoints for managing invitations to projects.")
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping("/create")
    public ResponseEntity<?> createInvitation( // tested
            @RequestParam("project-id") @NotEmpty @NotNull @NotBlank String projectId,
            @RequestParam("emails") @NotEmpty @NotNull List<@NotBlank @Email String> emails,
            Authentication auth) throws MessagingException {
        invitationService.createInvitation(projectId, emails, auth);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept/{project-id}") // tested
    public ResponseEntity<?> acceptInvitation(@PathVariable("project-id") String projectId, @RequestParam("invitation-code") String invitationCode, Authentication auth) {
        invitationService.acceptInvitation(projectId, invitationCode, auth);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove") // tested
    public ResponseEntity<?> removeMemberFromProject(
            @RequestParam("project-id") String projectId,
            @RequestParam("user-id") String userId,
            Authentication auth) {
        invitationService.removeMemberFromProject(projectId, userId, auth);
        return ResponseEntity.ok().build();
    }
}
