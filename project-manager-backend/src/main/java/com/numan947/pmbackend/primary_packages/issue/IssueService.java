package com.numan947.pmbackend.primary_packages.issue;

import com.numan947.pmbackend.primary_packages.issue.dto.IssueRequest;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueShortResponse;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;

public interface IssueService {
    IssueShortResponse createIssue(IssueRequest issueRequest, Authentication auth);

    Optional<IssueResponse> getIssue(String projectId, String issueId, Authentication auth);

    List<IssueShortResponse>getIssuesForProject(String projectId, Authentication auth, Integer page, Integer size);

    IssueShortResponse updateIssue(IssueRequest issueRequest, Authentication auth);

    IssueShortResponse updateIssuePriority(String issueId, String priority, Authentication auth);

    IssueShortResponse updateIssueDueDate(String issueId, String dueDate, Authentication auth);

    IssueShortResponse updateIssueTags(String issueId, List<String> tags, Authentication auth);

    IssueShortResponse updateIssueAssignedUser(String issueId, String assignedUserId, Authentication auth);

    void deleteIssue(String issueId, Authentication auth);

    IssueResponse addCommentToIssue(String issueId, String comment, Authentication auth);

    IssueResponse deleteCommentFromIssue(String issueId, String commentId, Authentication auth);

    IssueResponse updateCommentInIssue(String issueId, String commentId, String comment, Authentication auth);

    IssueResponse getIssueComments(String issueId, Authentication auth);

    List<IssueShortResponse> searchIssues(String projectId, String query, Authentication auth);
}
