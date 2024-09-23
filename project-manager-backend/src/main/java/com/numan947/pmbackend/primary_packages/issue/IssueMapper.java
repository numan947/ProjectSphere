package com.numan947.pmbackend.primary_packages.issue;

import com.numan947.pmbackend.primary_packages.comments.CommentMapper;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueRequest;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueShortResponse;
import com.numan947.pmbackend.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class IssueMapper {
    private final UserMapper userMapper;
    private final CommentMapper commentMapper;
    public IssueResponse toIssueResponse(Issue issue) {
        return IssueResponse.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .description(issue.getDescription())
                .status(issue.getStatus())
                .priority(issue.getPriority())
                .dueDate(issue.getDueDate())
                .tags(issue.getTags())
                .assignedUser(userMapper.toBriefUserResponse(issue.getAssignedUser()))
                .createdBy(userMapper.toBriefUserResponse(issue.getCreatedBy()))
                .lastUpdatedBy(issue.getLastUpdatedBy())
                .comments(issue.getComments().stream().map(commentMapper::toCommentResponse).toList())
                .build();
    }

    public IssueShortResponse toIssueShortResponse(Issue issue) {
        return IssueShortResponse.builder()
                .id(issue.getId())
                .title(issue.getTitle())
                .status(issue.getStatus())
                .priority(issue.getPriority())
                .dueDate(issue.getDueDate())
                .tags(issue.getTags())
                .assignedUser(userMapper.toBriefUserResponse(issue.getAssignedUser()))
                .createdBy(userMapper.toBriefUserResponse(issue.getCreatedBy()))
                .build();
    }

    public Issue toIssue(IssueRequest issueRequest) {
        Issue m = Issue.builder()
                .title(issueRequest.title())
                .description(issueRequest.description())
                .status(issueRequest.status())
                .priority(issueRequest.priority())
                .dueDate(issueRequest.dueDate())
                .tags(new ArrayList<>())
                .comments(new ArrayList<>())
                .build();

        if (issueRequest.issueId() != null) {
            m.setId(issueRequest.issueId());
        }
        if (issueRequest.tags() != null) {
            m.getTags().addAll(issueRequest.tags());
        }
        return m;
    }
}
