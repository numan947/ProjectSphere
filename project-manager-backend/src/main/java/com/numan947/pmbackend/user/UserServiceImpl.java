package com.numan947.pmbackend.user;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.primary_packages.issue.Issue;
import com.numan947.pmbackend.primary_packages.issue.IssueMapper;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueShortResponse;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.primary_packages.project.ProjectMapper;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectResponse;
import com.numan947.pmbackend.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;
    private final IssueMapper issueMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void addProjectToUser(String userId, Project project) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        user.getProjects().add(project);
        user.setNumberOfProjects(user.getNumberOfProjects() + 1);
        userRepository.save(user);
    }

    @Override
    public void removeProjectFromUser(String userId, Project project) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        user.getProjects().removeIf(p -> p.getId().equals(project.getId()));
        user.setNumberOfProjects(user.getNumberOfProjects() - 1);
        userRepository.save(user);
    }

    @Override
    public void addIssueToUser(String userId, Issue issue) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        user.getCreatedIssues().add(issue);
        userRepository.save(user);
    }

    @Override
    public void removeIssueFromUser(String userId, Issue issue) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        user.getCreatedIssues().removeIf(i -> i.getId().equals(issue.getId()));
        userRepository.save(user);
    }

    @Override
    public void assignIssueToUser(String userId, Issue issue) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        user.getAssignedIssues().add(issue);
        userRepository.save(user);
    }

    @Override
    public void unassignIssueFromUser(String userId, Issue issue) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        user.getAssignedIssues().removeIf(i -> i.getId().equals(issue.getId()));
        userRepository.save(user);
    }

    @Override
    public UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        List<ProjectResponse> ownProjects = new ArrayList<>();
        List<ProjectResponse> otherProjects = new ArrayList<>();
        for (Project project : user.getProjects()) {
            if (project.getOwner().getId().equals(userId)) {
                ownProjects.add(projectMapper.toBriefProjectResponse(project));
            } else {
                otherProjects.add(projectMapper.toBriefProjectResponse(project));
            }
        }
        System.out.println(ownProjects.size());
        List<IssueShortResponse> createdIssues = user.getCreatedIssues().stream().map(issueMapper::toIssueShortResponse).toList();
        List<IssueShortResponse> assignedIssues = user.getAssignedIssues().stream().map(issueMapper::toIssueShortResponse).toList();
        return userMapper.toUserProfileResponse(user, ownProjects, otherProjects, createdIssues, assignedIssues);
    }
}
