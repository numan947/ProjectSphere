package com.numan947.pmbackend.user;

import com.numan947.pmbackend.primary_packages.issue.Issue;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.user.dto.UserResponse;

import java.util.Optional;

public interface UserService {
    // this is the only method exposed to the controller
    UserResponse getUserProfile(String userId);

    // these methods are used from other services
    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(String userId);
    void addProjectToUser(String userId, Project project);
    void removeProjectFromUser(String userId, Project project);
    void addIssueToUser(String userId, Issue issue);
    void removeIssueFromUser(String userId, Issue issue);
    void assignIssueToUser(String userId, Issue issue);
    void unassignIssueFromUser(String userId, Issue issue);
}
