package com.numan947.pmbackend.primary_packages.issue.dto;

import com.numan947.pmbackend.user.dto.UserResponse;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IssueShortResponse {
    private String id;
    private String title;;
    private String description;;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private List<String> tags;
    private UserResponse assignedUser;
}
