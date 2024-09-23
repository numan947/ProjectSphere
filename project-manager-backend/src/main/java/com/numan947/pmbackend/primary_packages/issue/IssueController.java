package com.numan947.pmbackend.primary_packages.issue;

import com.numan947.pmbackend.primary_packages.issue.dto.IssueRequest;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueShortResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/issues")
@RequiredArgsConstructor
@Tag(name = "Issue", description = "Issue API")
public class IssueController {
    private final IssueService issueService;
    private final String defaultPage = "0";
    private final String defaultPageSize = "5";

    @PostMapping
    public ResponseEntity<IssueShortResponse> createIssue( // Create an issue // tested
            @RequestBody @Valid IssueRequest issueRequest,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.createIssue(issueRequest, auth));
    }

    @GetMapping
    public ResponseEntity<List<IssueShortResponse>> getAllIssues( // Get all issues for a project // tested
            @RequestParam("project-id") @Valid @NotNull @NotEmpty @NotBlank String projectId,
            @RequestParam(value = "tag", defaultValue = "") List<String> tags,
            @RequestParam(defaultValue = defaultPage) Integer page,
            @RequestParam(defaultValue = defaultPageSize) Integer size,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.getIssuesForProject(projectId, tags, auth, page, size));
    }

    @PutMapping
    public ResponseEntity<IssueShortResponse> updateIssue( // Update an issue // tested
            @RequestBody @Valid IssueRequest issueRequest,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateIssue(issueRequest,auth));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteIssue(
            @RequestParam("project-id") @Valid @NotEmpty @NotBlank @NotNull String projectId,
            @RequestParam("issue-id") @Valid @NotEmpty @NotBlank @NotNull String issueId,
            Authentication auth
    ) {
        issueService.deleteIssue(projectId, issueId, auth);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/issue")
    public ResponseEntity<IssueResponse> getIssueById( // Get an issue by id // tested
                                                       @RequestParam("project-id") String projectId,
                                                       @RequestParam("issue-id") String issueId,
                                                       Authentication auth
    ) {
        return ResponseEntity.ok(issueService.getIssue(projectId, issueId, auth));
    }

    // update priority, status, due-date, tags, assigned-user
    @PatchMapping("/set-priority")
    public ResponseEntity<IssueShortResponse> updateIssuePriority( // Update issue priority // tested
            @RequestParam("project-id") @Valid @NotEmpty @NotBlank @NotNull String projectId,
            @RequestParam("issue-id") @Valid @NotEmpty @NotBlank @NotNull String issueId,
            @RequestParam("priority") @Valid @NotEmpty @NotBlank @NotNull String priority,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateIssuePriority(projectId, issueId, priority, auth));
    }

    @PatchMapping("/set-due-date")
    public ResponseEntity<IssueShortResponse> updateIssueDueDate( // Update issue due date // tested
            @RequestParam("project-id") @Valid @NotEmpty @NotBlank @NotNull String projectId,
            @RequestParam("issue-id") @Valid @NotEmpty @NotBlank @NotNull String issueId,
            @RequestParam("due-date") @Valid @NotNull @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dueDate,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateIssueDueDate(projectId, issueId, dueDate, auth));
    }

    @PatchMapping("/set-tags")
    public ResponseEntity<IssueShortResponse> updateIssueTags( // Update issue tags // tested
            @RequestParam("project-id") @Valid @NotEmpty @NotBlank @NotNull String projectId,
            @RequestParam("issue-id") @Valid @NotEmpty @NotBlank @NotNull String issueId,
            @RequestParam("tags") @Valid @NotEmpty @NotNull List<String> tags,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateIssueTags(projectId, issueId, tags, auth));
    }

    @PatchMapping("/assign-to")
    public ResponseEntity<IssueShortResponse> updateIssueAssignedUser(
            @RequestParam("project-id") @Valid @NotEmpty @NotBlank @NotNull String projectId,
            @RequestParam("issue-id") @Valid @NotEmpty @NotBlank @NotNull String issueId,
            @RequestParam("user-id") @Valid @NotEmpty @NotBlank @NotNull String assignedUserId,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateIssueAssignedUser(projectId, issueId, assignedUserId, auth));
    }

    @PatchMapping("/set-status")
    public ResponseEntity<IssueShortResponse> updateIssueStatus( // Update issue status // tested
            @RequestParam("project-id") @Valid @NotEmpty @NotBlank @NotNull String projectId,
            @RequestParam("issue-id") @Valid @NotEmpty @NotBlank @NotNull String issueId,
            @RequestParam("status") @Valid @NotEmpty @NotBlank @NotNull String status,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.updateIssueStatus(projectId, issueId, status, auth));
    }


    @GetMapping("/search")
    public ResponseEntity<List<IssueShortResponse>> searchIssues(
            @RequestParam("project-id")  @Valid @NotEmpty @NotBlank @NotNull String projectId,
            @RequestParam("query")  @Valid @NotEmpty @NotBlank @NotNull String query,
            Authentication auth
    ) {
        return ResponseEntity.ok(issueService.searchIssues(projectId, query, auth));
    }

}