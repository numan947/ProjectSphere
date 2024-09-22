package com.numan947.pmbackend.primary_packages.chatmessage;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "_chat_messages")
public class Message extends BaseEntity {
    private String content;
    private LocalDateTime date;


    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnore
    private Chat chat;
}
