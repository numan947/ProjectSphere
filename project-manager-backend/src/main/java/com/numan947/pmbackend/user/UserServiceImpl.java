package com.numan947.pmbackend.user;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.primary_packages.issue.Issue;
import com.numan947.pmbackend.primary_packages.issue.IssueService;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.primary_packages.project.ProjectMapper;
import com.numan947.pmbackend.primary_packages.project.ProjectService;
import com.numan947.pmbackend.primary_packages.project.dto.ProjectResponse;
import com.numan947.pmbackend.security.jwt.JWTService;
import com.numan947.pmbackend.user.dto.UserResponse;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final ProjectService projectService;
    private final IssueService issueService;

    private final UserMapper userMapper;
    private final ProjectMapper projectMapper;

    @Override
    public UserResponse findByJwt(String jwt) {
        Claims allClaimsFromToken = jwtService.extractAllClaims(jwt);
        String email = jwtService.extractClaim(allClaimsFromToken, Claims::getSubject);
        return findByEmail(email);
    }

    @Override
    public UserResponse findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse findByUserId(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateProjectSize(String userId, Boolean isIncrement) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        if (isIncrement)
            user.setNumberOfProjects(user.getNumberOfProjects() + 1);
        else
            user.setNumberOfProjects(user.getNumberOfProjects() - 1);
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserProfile(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new OperationNotPermittedException("User not found"));
        List<ProjectResponse> ownProjects = user.getProjects().stream().map(projectMapper::toProjectResponse).toList();
        List<ProjectResponse>otherProjects = projectService.getAllTeamProjectsOfUser(userId);
        List<IssueResponse> createdIssues = issueService.getAllIssuesCreatedByUser(userId);
        List<IssueResponse> assignedIssues = issueService.getAllIssuesAssignedToUser(userId);
        return userMapper.toUserProfileResponse(user, ownProjects, otherProjects, createdIssues, assignedIssues);
    }
}
