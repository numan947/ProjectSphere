package com.numan947.pmbackend.primary_packages.chatmessage;

import com.numan947.pmbackend.primary_packages.chatmessage.dto.ChatResponse;
import com.numan947.pmbackend.primary_packages.chatmessage.dto.MessageResponse;
import com.numan947.pmbackend.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ChatMapper {
    private final UserMapper userMapper;
    public ChatResponse toChatResponse(Chat chat) {
        return ChatResponse.builder()
                .id(chat.getId())
                .headline(chat.getChatHeadLine())
                .messages(chat.getMessages().stream().map(this::toMessageResponse).collect(Collectors.toList()))
                .members(chat.getMembers().stream().map(userMapper::toUserResponse).collect(Collectors.toList()))
                .build();
    }

    private MessageResponse toMessageResponse(Message message) {
        return MessageResponse.builder()
                .id(message.getId())
                .content(message.getContent())
                .user(userMapper.toUserResponse(message.getUser()))
                .date(message.getDate())
                .build();
    }
}
