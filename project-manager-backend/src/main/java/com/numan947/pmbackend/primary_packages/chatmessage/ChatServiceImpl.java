package com.numan947.pmbackend.primary_packages.chatmessage;

import com.numan947.pmbackend.primary_packages.chatmessage.dto.ChatResponse;
import com.numan947.pmbackend.primary_packages.project.Project;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService{
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;
    @Override
    public Chat createNewChat(String chatHeadLine, Project project) {
        Chat chat = new Chat(
                chatHeadLine,
                new ArrayList<>(), // empty list of messages
                project,
                new ArrayList<>() // empty list of users
        );// add all team members to the chat
        chat.getMembers().addAll(project.getTeamMembers()); // add owner to the chat
        chatRepository.save(chat);
        return chat;
    }

    @Override
    public ChatResponse getChatByProjectId(String projectId) {
        Chat chat = chatRepository.findChatByProjectId(projectId).orElseThrow(() -> new EntityNotFoundException("Chat not found"));
        return chatMapper.toChatResponse(chat);
    }
}
