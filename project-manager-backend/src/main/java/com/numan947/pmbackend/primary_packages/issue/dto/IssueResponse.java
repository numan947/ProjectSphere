package com.numan947.pmbackend.primary_packages.issue.dto;

import com.numan947.pmbackend.primary_packages.issue.comments.dto.CommentResponse;
import com.numan947.pmbackend.user.dto.UserResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueResponse {
    private String id;
    private String title;;
    private String description;;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private List<String> tags;
    private UserResponse assignedUser;
    private List<CommentResponse>comments;
}
