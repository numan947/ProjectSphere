package com.numan947.pmbackend.primary_packages.comments.dto;

import com.numan947.pmbackend.user.dto.UserResponse;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String id;
    private String content;
    private LocalDateTime date;
    private UserResponse user;
}
