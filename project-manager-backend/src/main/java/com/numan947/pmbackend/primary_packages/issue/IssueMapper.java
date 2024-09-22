package com.numan947.pmbackend.primary_packages.issue;

import com.numan947.pmbackend.primary_packages.comments.CommentMapper;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
                .assignedUser(userMapper.toUserResponse(issue.getAssignedUser()))
                .comments(issue.getComments().stream().map(commentMapper::toCommentResponse).toList())
                .build();
    }
}
