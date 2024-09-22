package com.numan947.pmbackend.primary_packages.chatmessage.dto;

import com.numan947.pmbackend.user.dto.UserResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ChatResponse{
    public String id;
    public String headline;
    List<MessageResponse>messages;
    List<UserResponse> members;
}
