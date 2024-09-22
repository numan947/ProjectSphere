package com.numan947.pmbackend.primary_packages.issue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.primary_packages.comments.Comment;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_issues")
public class Issue extends BaseEntity {

    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;

    @ElementCollection
    @CollectionTable(name = "_issue_tags", joinColumns = @JoinColumn(name = "issue_id"))
    private List<String>tags;

    @ManyToOne
    private User assignedUser;

    @ManyToOne
    @JsonIgnore
    private Project project;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment>comments;
}
