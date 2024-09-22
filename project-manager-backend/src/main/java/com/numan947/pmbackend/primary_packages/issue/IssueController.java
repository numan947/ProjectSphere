package com.numan947.pmbackend.primary_packages.issue;

import com.numan947.pmbackend.primary_packages.issue.dto.IssueRequest;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueShortResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
@Tag(name = "Issue", description = "Issue API")
public class IssueController {
    private final IssueService issueService;
    private final String defaultPage = "0";
    private final String defaultPageSize = "5";

    @GetMapping
    public ResponseEntity<List<IssueShortResponse>> getAllIssues(
            @RequestParam("project-id") String projectId,
            @RequestParam(defaultValue = defaultPage) Integer page,
            @RequestParam(defaultValue = defaultPageSize) Integer size,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.getIssuesForProject(projectId, auth, page, size));
    }

    @GetMapping
    public ResponseEntity<IssueResponse> getIssueById(
            @RequestParam("project-id") String projectId,
            @RequestParam("issue-id") String issueId,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.getIssue(projectId, issueId, auth).orElseThrow());
    }

    @PostMapping
    public ResponseEntity<IssueShortResponse> createIssue(
            @RequestBody @Valid IssueRequest issueRequest,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.createIssue(issueRequest, auth));
    }


    @PutMapping
    public ResponseEntity<IssueShortResponse> updateIssue(
            @RequestBody IssueRequest issueRequest,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateIssue(issueRequest,auth));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteIssue(
            @RequestParam("issue-id") String issueId,
            Authentication auth
    ) {
        issueService.deleteIssue(issueId, auth);
        return ResponseEntity.ok().build();
    }

    @PatchMapping
    public ResponseEntity<IssueShortResponse> updateIssuePriority(
            @RequestParam("issue-id") String issueId,
            @RequestParam("priority") String priority,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateIssuePriority(issueId, priority, auth));
    }

    @PatchMapping
    public ResponseEntity<IssueShortResponse> updateIssueDueDate(
            @RequestParam("issue-id") String issueId,
            @RequestParam("due-date") String dueDate,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateIssueDueDate(issueId, dueDate, auth));
    }

    @PatchMapping
    public ResponseEntity<IssueShortResponse> updateIssueTags(
            @RequestParam("issue-id") String issueId,
            @RequestParam("tags") List<String> tags,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateIssueTags(issueId, tags, auth));
    }

    @PatchMapping
    public ResponseEntity<IssueShortResponse> updateIssueAssignedUser(
            @RequestParam("issue-id") String issueId,
            @RequestParam("assigned-user-id") String assignedUserId,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateIssueAssignedUser(issueId, assignedUserId, auth));
    }


    @PostMapping
    public ResponseEntity<IssueResponse> addCommentToIssue(
            @RequestParam("issue-id") String issueId,
            @RequestParam("comment") String comment,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.addCommentToIssue(issueId, comment, auth));
    }

    @DeleteMapping
    public ResponseEntity<IssueResponse> deleteCommentFromIssue(
            @RequestParam("issue-id") String issueId,
            @RequestParam("comment-id") String commentId,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.deleteCommentFromIssue(issueId, commentId, auth));
    }

    @PatchMapping
    public ResponseEntity<IssueResponse> updateCommentInIssue(
            @RequestParam("issue-id") String issueId,
            @RequestParam("comment-id") String commentId,
            @RequestParam("comment") String comment,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateCommentInIssue(issueId, commentId, comment, auth));
    }

    @GetMapping
    public ResponseEntity<IssueResponse> getIssueComments(
            @RequestParam("issue-id") String issueId,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.getIssueComments(issueId, auth));
    }

    @GetMapping("/search")
    public ResponseEntity<List<IssueShortResponse>> searchIssues(
            @RequestParam("project-id") String projectId,
            @RequestParam("query") String query,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.searchIssues(projectId, query, auth));
    }

}
