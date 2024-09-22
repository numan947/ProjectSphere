package com.numan947.pmbackend.user;

import com.numan947.pmbackend.primary_packages.issue.Issue;
import com.numan947.pmbackend.primary_packages.issue.IssueMapper;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.primary_packages.project.ProjectMapper;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectResponse;
import com.numan947.pmbackend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    public UserResponse toBriefUserResponse(User user){
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .build();
    }
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fname(user.getFirstName())
                .lname(user.getLastName())
                .totalProjects(user.getNumberOfProjects())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .build();
    }

    public UserResponse toUserProfileResponse(
            User user,
            List<ProjectResponse>ownProjects,
            List<ProjectResponse>otherProject,
            List<IssueResponse>createdIssues,
            List<IssueResponse>assignedIssues) {
        UserResponse tmp = UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fname(user.getFirstName())
                .lname(user.getLastName())
                .totalProjects(user.getNumberOfProjects())
                .fullName(user.getFirstName() + " " + user.getLastName())
                .createdIssues(new ArrayList<>())
                .assignedIssues(new ArrayList<>())
                .ownProjects(new ArrayList<>())
                .teamProjects(new ArrayList<>())
                .build();

        if(ownProjects != null) {
            tmp.getOwnProjects().addAll(ownProjects);
        }
        if(otherProject != null) {
            tmp.getTeamProjects().addAll(otherProject);
        }
        if(createdIssues != null) {
            tmp.getCreatedIssues().addAll(createdIssues);
        }
        if(assignedIssues != null) {
            tmp.getAssignedIssues().addAll(assignedIssues);
        }

        return tmp;
    }
}
