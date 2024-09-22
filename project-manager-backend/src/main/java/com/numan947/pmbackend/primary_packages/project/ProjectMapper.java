package com.numan947.pmbackend.primary_packages.project;

import com.numan947.pmbackend.primary_packages.issue.IssueMapper;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectRequest;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectResponse;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectShortResponse;
import com.numan947.pmbackend.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class ProjectMapper {
    private final UserMapper userMapper;
    private final IssueMapper issueMapper;
    public Project toProject(ProjectRequest projectRequest) {
        Project p = Project.builder()
                .name(projectRequest.name())
                .description(projectRequest.description())
                .category(projectRequest.category())
                .tags(new ArrayList<>())
                .chat(null)
                .owner(null)
                .issues(new ArrayList<>())
                .teamMembers(new ArrayList<>())
                .build();
        if(projectRequest.id() != null) {
            p.setId(projectRequest.id());
        }
        if (projectRequest.tags() != null) {
            p.getTags().addAll(projectRequest.tags());
        }

        return p;
    }

    public ProjectShortResponse toProjectShortResponse(Project project) {
        return ProjectShortResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .category(project.getCategory())
                .tags(project.getTags())
                .memberCount(project.getTeamMembers().size())
                .issueCount(project.getIssues().size())
                .owner(userMapper.toUserResponse(project.getOwner()))
                .build();
    }

    public ProjectResponse toProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .category(project.getCategory())
                .tags(project.getTags())
                .issues(project.getIssues().stream().map(issueMapper::toIssueResponse).toList())
                .teamMembers(project.getTeamMembers().stream().map(userMapper::toUserResponse).toList())
                .owner(userMapper.toUserResponse(project.getOwner()))
                .build();
    }
}
