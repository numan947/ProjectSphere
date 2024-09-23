package com.numan947.pmbackend.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueShortResponse;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectResponse;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse
{
    String id;
    String email;
    String fname;
    String lname;
    Long totalProjects;
    String fullName;

    List<IssueShortResponse>createdIssues;
    List<IssueShortResponse> assignedIssues; // assigned issues
    List<ProjectResponse> ownProjects;
    List<ProjectResponse> teamProjects;
}
