package com.numan947.pmbackend.primary_packages.project;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.numan947.pmbackend.common.BaseEntity;
import com.numan947.pmbackend.primary_packages.issue.Issue;
import com.numan947.pmbackend.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "_projects")
public class Project extends BaseEntity {
    private String name;
    private String description;
    private String category;

    @ElementCollection
    @CollectionTable(name = "_project_tags", joinColumns = @JoinColumn(name = "project_id"))
    private List<String> tags;

//    @JsonIgnore
//    @OneToOne(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Chat chat;

    @ManyToOne
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Issue> issues;

    @ManyToMany
    @JsonIgnore
    private List<User>teamMembers;

}
