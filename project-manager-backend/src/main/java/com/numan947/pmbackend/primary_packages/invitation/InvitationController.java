package com.numan947.pmbackend.primary_packages.invitation;

import jakarta.mail.MessagingException;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/invitation")
public class InvitationController {
    private final InvitationService invitationService;

    @PostMapping("/create")
    public ResponseEntity<?> createInvitation(
            @RequestParam("project-id") @NotEmpty @NotNull @NotBlank String projectId,
            @RequestParam("user-email") @Email @NotBlank @NotEmpty @NotNull String userEmail,
            Authentication auth) throws MessagingException {
        invitationService.createInvitation(projectId, userEmail, auth);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteInvitation(@RequestParam("invitation-code") String invitationCode, @RequestParam("project-id") String projectId, Authentication auth) {
        invitationService.deleteInvitation(projectId, invitationCode, auth);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/accept/{project-id}")
    public ResponseEntity<?> acceptInvitation(@PathVariable("project-id") String projectId, @RequestParam("invitation-code") String invitationCode, Authentication auth) {
        invitationService.acceptInvitation(projectId, invitationCode, auth);
        return ResponseEntity.ok().build();
    }
}
