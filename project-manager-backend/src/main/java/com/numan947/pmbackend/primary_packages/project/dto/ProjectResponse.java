package com.numan947.pmbackend.primary_packages.project.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.user.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectResponse {
    private String id;
    private String name;
    private String description;
    private List<String> categories;
    private List<String> tags;
    private List<IssueResponse>issues;
    private List<UserResponse>teamMembers;
    private UserResponse owner;
}
