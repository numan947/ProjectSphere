package com.numan947.pmbackend.primary_packages.project.dto;

import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.user.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProjectResponse {
    private String id;
    private String name;
    private String description;
    private String category;
    private List<String> tags;
    private List<IssueResponse>issues;
    private List<UserResponse>teamMembers;
    private UserResponse owner;
}
