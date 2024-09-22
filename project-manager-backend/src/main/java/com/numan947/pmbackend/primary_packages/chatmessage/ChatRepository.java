package com.numan947.pmbackend.primary_packages.chatmessage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, String> {
    public Optional<Chat>findChatByProjectId(String projectId);
}
