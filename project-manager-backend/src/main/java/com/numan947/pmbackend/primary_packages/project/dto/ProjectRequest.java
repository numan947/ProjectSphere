package com.numan947.pmbackend.primary_packages.project.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProjectRequest(
        String id,

        @NotNull(message = "Project name is required")
        @NotEmpty(message = "Project name is required")
        @NotBlank(message = "Project name is required")
        String name,

        String description,

        List<String> categories,

        List<String> tags
) {
}
