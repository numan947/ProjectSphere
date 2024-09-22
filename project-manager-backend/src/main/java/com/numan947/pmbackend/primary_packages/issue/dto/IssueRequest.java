package com.numan947.pmbackend.primary_packages.issue.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record IssueRequest(
        String issueId,

        @NotNull(message = "Project Id is required")
        @NotEmpty(message = "Project Id is required")
        @NotBlank(message = "Project Id is required")
        String projectId,

        @NotNull(message = "Title is required")
        @NotEmpty(message = "Title is required")
        @NotBlank(message = "Title is required")
        String title,

        String description,

        @NotNull(message = "Status is required")
        @NotEmpty(message = "Status is required")
        @NotBlank(message = "Status is required")
        String status,

        @NotNull(message = "Priority is required")
        @NotEmpty(message = "Priority is required")
        @NotBlank(message = "Priority is required")
        String priority,

        String assignedUserId,

        LocalDate dueDate,

        List<String> tags
) {
}
