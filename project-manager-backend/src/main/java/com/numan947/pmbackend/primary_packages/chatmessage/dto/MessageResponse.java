package com.numan947.pmbackend.primary_packages.chatmessage.dto;

import com.numan947.pmbackend.user.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class MessageResponse {
    private String id;
    private String content;
    private UserResponse user;
    private LocalDateTime date;
}
