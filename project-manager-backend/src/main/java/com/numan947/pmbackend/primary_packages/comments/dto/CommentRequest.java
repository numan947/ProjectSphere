package com.numan947.pmbackend.primary_packages.comments.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record CommentRequest(
        String commentId,// for editing
        @NotNull(message = "Content cannot be null")
        @NotEmpty(message = "Content cannot be empty")
        @NotBlank(message = "Content cannot be blank")
        String content,
        @NotNull(message = "Issue id cannot be null")
        @NotEmpty(message = "Issue id cannot be empty")
        @NotBlank(message = "Issue id cannot be blank")
        String issueId,
        @NotNull(message = "Project id cannot be null")
        @NotEmpty(message = "Project id cannot be empty")
        @NotBlank(message = "Project id cannot be blank")
        String projectId,
        @NotNull(message = "Date cannot be null")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime date // TODO: this is probably not a good idea to let the user set the date
) {
}
