package com.numan947.pmbackend.primary_packages.chatmessage;

import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_chats")
@Getter
@Setter
public class Chat extends BaseEntity {
    private String ChatHeadLine;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToMany(mappedBy = "chats")
    private List<User> members;
}
