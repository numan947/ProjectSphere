package com.numan947.pmbackend.primary_packages.issue;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueRequest;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueShortResponse;
import com.numan947.pmbackend.primary_packages.project.Project;
import com.numan947.pmbackend.primary_packages.project.ProjectService;
import com.numan947.pmbackend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService{
    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;
    private final ProjectService projectService;


    @Override
    public List<IssueShortResponse> getIssuesForProject(String projectId, Authentication auth, Integer page, Integer size) {
        if (projectId == null) {
            throw new OperationNotPermittedException("Project id is required for fetching issues");
        }

        User user = (User) auth.getPrincipal();
        if(!projectService.isUserPartOfProject(user.getId(), projectId)) {
            throw new OperationNotPermittedException("User is not part of the project");
        }
        return issueRepository.findAllByProjectId(projectId).stream().map(issueMapper::toIssueShortResponse).toList();
    }


    @Override
    public IssueShortResponse createIssue(IssueRequest issueRequest, Authentication auth) {
        // check if user is part of the project, only members can create issues
        User user = (User) auth.getPrincipal();
        Project project = projectService.findProjectById(issueRequest.projectId()).orElseThrow(
                () -> new OperationNotPermittedException("Project not found")
        );
        if(!project.getTeamMembers().contains(user)){
            throw new OperationNotPermittedException("User is not a member of this project");
        }

        Issue issue = issueMapper.toIssue(issueRequest);
        issue.setProject(project);
        issue.setAssignedUser(null); // initially no user is assigned
        issueRepository.save(issue);
        return issueMapper.toIssueShortResponse(issue);
    }

    @Override
    public Optional<IssueResponse> getIssue(String projectId, String issueId, Authentication auth) {
        if (issueId == null || projectId == null) {
            throw new OperationNotPermittedException("Issue id and project id are required for fetching an issue");
        }
        User user = (User) auth.getPrincipal();
        if(!projectService.isUserPartOfProject(user.getId(), projectId)) {
            throw new OperationNotPermittedException("User is not part of the project");
        }
        return issueRepository.findByProjectIdAndId(projectId, issueId).map(issueMapper::toIssueResponse);
    }

    @Override
    public IssueShortResponse updateIssue(IssueRequest issueRequest, Authentication auth) {
        if (issueRequest.issueId() == null) {
            throw new OperationNotPermittedException("Issue id is required for updating an issue");
        }
        User user = (User) auth.getPrincipal();
        if (!projectService.isUserPartOfProject(user.getId(), issueRequest.projectId())) {
            throw new OperationNotPermittedException("User is not part of the project");
        }
        Issue issue = issueRepository.findByProjectIdAndId(issueRequest.projectId(), issueRequest.issueId()).orElseThrow(
                () -> new OperationNotPermittedException("Issue not found")
        );
        issue.setTitle(issueRequest.title());
        issue.setDescription(issueRequest.description());
        issue.setStatus(issueRequest.status());
        issue.setPriority(issueRequest.priority());
        issue.setDueDate(issueRequest.dueDate());
        issue.setTags(issueRequest.tags());
        issueRepository.save(issue);
        return issueMapper.toIssueShortResponse(issue);
    }

    @Override
    public IssueShortResponse updateIssuePriority(String issueId, String priority, Authentication auth) {
        if (issueId == null) {
            throw new OperationNotPermittedException("Issue id is required for updating an issue");
        }
        return null;
    }

    @Override
    public IssueShortResponse updateIssueDueDate(String issueId, String dueDate, Authentication auth) {
        return null;
    }

    @Override
    public IssueShortResponse updateIssueTags(String issueId, List<String> tags, Authentication auth) {
        return null;
    }

    @Override
    public IssueShortResponse updateIssueAssignedUser(String issueId, String assignedUserId, Authentication auth) {
        return null;
    }

    @Override
    public void deleteIssue(String issueId, Authentication auth) {

    }

    @Override
    public IssueResponse addCommentToIssue(String issueId, String comment, Authentication auth) {
        return null;
    }

    @Override
    public IssueResponse deleteCommentFromIssue(String issueId, String commentId, Authentication auth) {
        return null;
    }

    @Override
    public IssueResponse updateCommentInIssue(String issueId, String commentId, String comment, Authentication auth) {
        return null;
    }

    @Override
    public IssueResponse getIssueComments(String issueId, Authentication auth) {
        return null;
    }

    @Override
    public List<IssueShortResponse> searchIssues(String projectId, String query, Authentication auth) {
        return List.of();
    }
}
