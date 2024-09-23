package com.numan947.pmbackend.primary_packages.issue;

import com.numan947.pmbackend.primary_packages.issue.dto.IssueRequest;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueShortResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.core.Authentication;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IssueService {
    IssueShortResponse createIssue(IssueRequest issueRequest, Authentication auth);

    IssueResponse getIssue(String projectId, String issueId, Authentication auth);

    List<IssueShortResponse>getIssuesForProject(String projectId, List<String>tags, Authentication auth, Integer page, Integer size);

    IssueShortResponse updateIssue(IssueRequest issueRequest, Authentication auth);

    IssueShortResponse updateIssuePriority(String projectId, String issueId, String priority, Authentication auth);

    IssueShortResponse updateIssueDueDate(String projectId, String issueId, LocalDate dueDate, Authentication auth);

    IssueShortResponse updateIssueTags(String projectId, String issueId, List<String> tags, Authentication auth);

    IssueShortResponse updateIssueAssignedUser(String projectId, String issueId, String assignedUserId, Authentication auth);

    IssueShortResponse updateIssueStatus(@Valid @NotEmpty @NotBlank @NotNull String projectId, @Valid @NotEmpty @NotBlank @NotNull String issueId, @Valid @NotEmpty @NotBlank @NotNull String status, Authentication auth);

    void deleteIssue(String projectId, String issueId, Authentication auth);

    List<IssueShortResponse> searchIssues(String projectId, String query, Authentication auth);

}
