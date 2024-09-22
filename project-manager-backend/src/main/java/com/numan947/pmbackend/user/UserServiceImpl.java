package com.numan947.pmbackend.user;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.primary_packages.issue.IssueMapper;
import com.numan947.pmbackend.primary_packages.issue.IssueService;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.primary_packages.project.ProjectMapper;
import com.numan947.pmbackend.primary_packages.project.ProjectService;
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
        List<IssueResponse> createdIssues = user.getCreatedIssues().stream().map(issueMapper::toIssueResponse).toList();
        List<IssueResponse> assignedIssues = user.getAssignedIssues().stream().map(issueMapper::toIssueResponse).toList();
        return userMapper.toUserProfileResponse(user, ownProjects, otherProjects, createdIssues, assignedIssues);
    }
}
