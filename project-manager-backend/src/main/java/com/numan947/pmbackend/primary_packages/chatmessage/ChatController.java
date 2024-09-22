package com.numan947.pmbackend.primary_packages.chatmessage;

import com.numan947.pmbackend.primary_packages.chatmessage.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatController {
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    @GetMapping("/{projectId}")
    public ResponseEntity<ChatResponse>getChatByProjectId(@PathVariable("projectId") String projectId){
        return ResponseEntity.ok(chatService.getChatByProjectId(projectId));
    }
}
