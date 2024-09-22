package com.numan947.pmbackend.primary_packages.chatmessage;

import com.numan947.pmbackend.primary_packages.chatmessage.dto.ChatResponse;
import com.numan947.pmbackend.primary_packages.project.Project;

public interface ChatService {
    Chat createNewChat(String chatHeadLine, Project project);

    ChatResponse getChatByProjectId(String projectId);
}
