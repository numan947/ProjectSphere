package com.numan947.pmbackend.primary_packages.comments;

import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.primary_packages.issue.Issue;
import com.numan947.pmbackend.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "_comments")
public class Comment extends BaseEntity {

    private String content;
    private LocalDateTime date;

    @ManyToOne
    private User user;
    @ManyToOne
    private Issue issue;
}
