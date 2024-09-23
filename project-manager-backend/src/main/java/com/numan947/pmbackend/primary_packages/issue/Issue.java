package com.numan947.pmbackend.primary_packages.issue;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.primary_packages.issue.comments.Comment;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "_issues")
public class Issue extends BaseEntity {

    private String title;
    private String description;
    private String status;
    private String priority;
    private LocalDate dueDate;
    private String lastUpdatedBy;

    @ElementCollection
    @CollectionTable(name = "_issue_tags", joinColumns = @JoinColumn(name = "issue_id"))
    private List<String>tags;

    @ManyToOne
    private User assignedUser;
    @ManyToOne
    private User createdBy;

    @ManyToOne
    @JsonIgnore
    private Project project;

    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Comment>comments;
}
