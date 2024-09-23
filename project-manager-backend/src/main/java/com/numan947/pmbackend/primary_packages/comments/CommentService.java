package com.numan947.pmbackend.primary_packages.comments;

import com.numan947.pmbackend.primary_packages.comments.dto.CommentRequest;
import com.numan947.pmbackend.primary_packages.comments.dto.CommentResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CommentService {
    CommentResponse addComment(@Valid CommentRequest commentRequest, Authentication auth);

    void deleteComment(@NotNull @NotEmpty @NotBlank @Valid String projectId, @NotNull @NotEmpty @NotBlank @Valid String issueId, @NotNull @NotEmpty @NotBlank @Valid String commentId, Authentication auth);

    List<CommentResponse> getComments(@NotNull @NotEmpty @NotBlank @Valid String projectId, @NotNull @NotEmpty @NotBlank @Valid String issueId, Authentication auth);

    CommentResponse editComment(@Valid CommentRequest commentRequest, Authentication auth);
}
