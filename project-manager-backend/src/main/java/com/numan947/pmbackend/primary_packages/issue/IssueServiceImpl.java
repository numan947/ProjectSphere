package com.numan947.pmbackend.primary_packages.issue;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.primary_packages.comments.Comment;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueRequest;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueResponse;
import com.numan947.pmbackend.primary_packages.issue.dto.IssueShortResponse;
import com.numan947.pmbackend.primary_packages.project.ProjectService;
import com.numan947.pmbackend.user.User;
import com.numan947.pmbackend.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueServiceImpl implements IssueService{
    private final IssueRepository issueRepository;
    private final IssueMapper issueMapper;
    private final ProjectService projectService;
    private final UserService userService;

    private Issue getIssueIfValid(String projectId, String issueId, String userId) {
        if (!projectService.isUserPartOfProject(userId, projectId)) {
            throw new OperationNotPermittedException("User is not part of the project");
        }
        Issue issue = issueRepository.findByProjectIdAndId(projectId, issueId).orElseThrow(
                () -> new OperationNotPermittedException("Issue not found")
        );

        if (!issue.getCreatedBy().getId().equals(userId)
                &&
                !issue.getProject().getOwner().getId().equals(userId)) {
            throw new OperationNotPermittedException("User is not allowed to update this issue");
        }
        return issue;
    }

    @Override
    public IssueShortResponse createIssue(IssueRequest issueRequest, Authentication auth) {
        // check if user is part of the project, only members can create issues
        User user = (User) auth.getPrincipal();
        if(!projectService.isUserPartOfProject(user.getId(), issueRequest.projectId())) {
            throw new OperationNotPermittedException("User is not a member of this project");
        }

        Issue issue = issueMapper.toIssue(issueRequest);
        issue.setProject(projectService.findProjectById(issueRequest.projectId()).orElseThrow( // --> update project
                () -> new OperationNotPermittedException("Project not found")
        ));
        issue.setAssignedUser(null); // initially no user is assigned
        issue.setCreatedBy(user); // set the user who created the issue --> update user
        issue.setLastUpdatedBy(user.getId()); // set the user who last updated the issue --> update user
        issueRepository.save(issue);

        projectService.addIssueToProject(issueRequest.projectId(), issue); // add the issue to the project
        userService.addIssueToUser(user.getId(), issue); // add the issue to the user
        return issueMapper.toIssueShortResponse(issue);
    }

    @Override
    public List<IssueShortResponse> getIssuesForProject(String projectId, List<String>tags, Authentication auth, Integer page, Integer size) {
        // TODO: Implement pagination later here
        if (projectId == null) {
            throw new OperationNotPermittedException("Project id is required for fetching issues");
        }
        User user = (User) auth.getPrincipal();
        if(!projectService.isUserPartOfProject(user.getId(), projectId)) {
            throw new OperationNotPermittedException("User is not part of the project");
        }
        List<IssueShortResponse>all = issueRepository.findAllByProjectId(projectId).stream().map(issueMapper::toIssueShortResponse).toList();
        // remove all empty tags from the list
        tags.removeIf(String::isBlank);
        if (tags.isEmpty()) {
            return all;
        }
        return all.stream().filter(issue -> new HashSet<>(issue.getTags()).containsAll(tags)).toList();
    }

    @Override
    public IssueShortResponse updateIssue(IssueRequest issueRequest, Authentication auth) {
        if (issueRequest.issueId() == null) {
            throw new OperationNotPermittedException("Issue id is required for updating an issue");
        } // check 1: issue id is required
        User user = (User) auth.getPrincipal();
        if (!projectService.isUserPartOfProject(user.getId(), issueRequest.projectId())) {
            throw new OperationNotPermittedException("User is not part of the project");
        } // check 2: user is part of the project
        Issue issue = issueRepository.findByProjectIdAndId(issueRequest.projectId(), issueRequest.issueId()).orElseThrow(
                () -> new OperationNotPermittedException("Issue not found")
        ); // check 3: issue exists

        //  Check 4: Only the creator of the issue or the project owner can update the issue
        if (!issue.getCreatedBy().getId().equals(user.getId())
                &&
            !issue.getProject().getOwner().getId().equals(user.getId())) {
            throw new OperationNotPermittedException("User is not allowed to update this issue");
        }


        issue.setTitle(issueRequest.title());
        issue.setDescription(issueRequest.description());
        issue.setStatus(issueRequest.status());
        issue.setPriority(issueRequest.priority());
        issue.setDueDate(issueRequest.dueDate());
        issue.setTags(issueRequest.tags());
        issue.setLastUpdatedBy(user.getId());
        issueRepository.save(issue);
        return issueMapper.toIssueShortResponse(issue);
    }

    @Override
    public IssueResponse getIssue(String projectId, String issueId, Authentication auth) {
        if (issueId == null || projectId == null) {
            throw new OperationNotPermittedException("Issue id and project id are required for fetching an issue");
        }
        User user = (User) auth.getPrincipal();
        if(!projectService.isUserPartOfProject(user.getId(), projectId)) {
            throw new OperationNotPermittedException("User is not part of the project");
        }
        return issueRepository.findByProjectIdAndId(projectId, issueId).map(issueMapper::toIssueResponse).orElseThrow(
                () -> new EntityNotFoundException("Issue not found")
        );
    }

    @Override
    public void deleteIssue(String projectId, String issueId, Authentication auth) {
        if (issueId == null || projectId == null) {
            throw new OperationNotPermittedException("Issue id and project id are required for deleting an issue");
        }
        //TODO: also delete comments --> after implementing comments, or we can cascade delete
        // delete issue, delete from project, delete from user, delete from assigned user
        User user = (User) auth.getPrincipal();
        if(!projectService.isUserPartOfProject(user.getId(), projectId)) {
            throw new OperationNotPermittedException("User is not part of the project");
        }
        Issue issue = issueRepository.findByProjectIdAndId(projectId, issueId).orElseThrow(
                () -> new OperationNotPermittedException("Issue not found")
        );
        // only the creator of the issue or the project owner can delete the issue
        if (!issue.getCreatedBy().getId().equals(user.getId())
                &&
            !issue.getProject().getOwner().getId().equals(user.getId())) {
            throw new OperationNotPermittedException("User is not allowed to delete this issue");
        }
        projectService.removeIssueFromProject(projectId, issue);
        userService.removeIssueFromUser(user.getId(), issue);
        if (issue.getAssignedUser() != null) {
            userService.unassignIssueFromUser(issue.getAssignedUser().getId(), issue);
        }
        issueRepository.delete(issue);
    }

    @Override
    public IssueShortResponse updateIssuePriority(String projectId, String issueId, String priority, Authentication auth) {
        if (projectId == null || issueId == null || priority == null) {
            throw new OperationNotPermittedException("Project id, issue id and priority are required for updating priority");
        }
        User user = (User) auth.getPrincipal();
        Issue issue = getIssueIfValid(projectId, issueId, user.getId());
        issue.setPriority(priority);
        issue.setLastUpdatedBy(user.getId());
        issueRepository.save(issue);
        return issueMapper.toIssueShortResponse(issue);
    }
    @Override
    public IssueShortResponse updateIssueDueDate(String projectId, String issueId, LocalDate dueDate, Authentication auth) {
        if (projectId == null || issueId == null || dueDate == null) {
            throw new OperationNotPermittedException("Project id, issue id and due date are required for updating due date");
        }
        User user = (User) auth.getPrincipal();
        Issue issue = getIssueIfValid(projectId, issueId, user.getId());
        issue.setDueDate(dueDate);
        issue.setLastUpdatedBy(user.getId());
        issueRepository.save(issue);
        return issueMapper.toIssueShortResponse(issue);
    }

    @Override
    public IssueShortResponse updateIssueTags(String projectId, String issueId, List<String> tags, Authentication auth) {
        if (projectId == null || issueId == null || tags == null) {
            throw new OperationNotPermittedException("Project id, issue id and tags are required for updating tags");
        }
        //make sure strings in tags are not empty
        if (tags.stream().anyMatch(String::isBlank)) {
            throw new OperationNotPermittedException("Tags cannot be empty");
        }
        User user = (User) auth.getPrincipal();
        Issue issue = getIssueIfValid(projectId, issueId, user.getId());
        issue.getTags().clear();
        issue.getTags().addAll(tags);
        issue.setLastUpdatedBy(user.getId());
        issueRepository.save(issue);
        return issueMapper.toIssueShortResponse(issue);
    }

    @Override
    public IssueShortResponse updateIssueAssignedUser(String projectId, String issueId, String assignedUserId, Authentication auth) {
        if (projectId == null || issueId == null || assignedUserId == null) {
            throw new OperationNotPermittedException("Project id, issue id and assigned user id are required for updating assigned user");
        }
        User user = (User) auth.getPrincipal();
        Issue issue = getIssueIfValid(projectId, issueId, user.getId());
        User newAssignedUser = userService.findByUserId(assignedUserId).orElseThrow(
                () -> new EntityNotFoundException("Assigned user not found")
        );
        // check if the new assigned user is part of the project
        if (!projectService.isUserPartOfProject(newAssignedUser.getId(), projectId)) {
            throw new OperationNotPermittedException("Assigned user is not part of the project");
        }
        // step 1: unassign the issue from the previous user
        if (issue.getAssignedUser() != null) {
            userService.unassignIssueFromUser(issue.getAssignedUser().getId(), issue);
        }
        // step 2: assign the issue to the new user
        issue.setAssignedUser(newAssignedUser);
        issue.setLastUpdatedBy(user.getId());
        issueRepository.save(issue);
        userService.assignIssueToUser(assignedUserId, issue);
        return issueMapper.toIssueShortResponse(issue);
    }

    @Override
    public IssueShortResponse updateIssueStatus(String projectId, String issueId, String status, Authentication auth) {
        if (projectId == null || issueId == null || status == null) {
            throw new OperationNotPermittedException("Project id, issue id and status are required for updating status");
        }
        //issue status can be updated by the creator of the issue, project owner, or the assigned user
        User user = (User) auth.getPrincipal();
        Issue issue = issueRepository.findByProjectIdAndId(projectId, issueId).orElseThrow(
                () -> new OperationNotPermittedException("Issue not found")
        );
        if (!issue.getCreatedBy().getId().equals(user.getId())
                &&
            !issue.getProject().getOwner().getId().equals(user.getId())
                &&
            (issue.getAssignedUser() == null || !issue.getAssignedUser().getId().equals(user.getId()))) {
            throw new OperationNotPermittedException("User is not allowed to update this issue");
        }
        issue.setStatus(status);
        issue.setLastUpdatedBy(user.getId());
        issueRepository.save(issue);
        return issueMapper.toIssueShortResponse(issue);
    }


    @Override
    public List<IssueShortResponse> searchIssues(String projectId, String query, Authentication auth) {
        //TODO: Implement pagination later here
        // search is only allowed for project members
        if (projectId == null || query == null) {
            throw new OperationNotPermittedException("Project id and query are required for searching issues");
        }
        User user = (User) auth.getPrincipal();
        if(!projectService.isUserPartOfProject(user.getId(), projectId)) {
            throw new OperationNotPermittedException("User is not part of the project");
        }
        return issueRepository.searchIssues(projectId, query).stream().map(issueMapper::toIssueShortResponse).toList();
    }

    @Override
    public boolean issueExists(String projectId, String issueId) {
        return issueRepository.existsByProjectIdAndId(projectId, issueId);
    }

    @Override
    public Optional<Issue> getIssueByProjectIdAndIssueId(String projectId, String issueId) {
        return issueRepository.findByProjectIdAndId(projectId, issueId);
    }

    @Override
    public void addCommentToIssue(String issueId, Comment cmnt) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(
                () -> new EntityNotFoundException("Issue not found")
        );
        issue.getComments().add(cmnt);
        issueRepository.save(issue);
    }

    @Override
    public void removeCommentFromIssue(String issueId, Comment cmnt) {
        Issue issue = issueRepository.findById(issueId).orElseThrow(
                () -> new EntityNotFoundException("Issue not found")
        );
        issue.getComments().removeIf(comment -> comment.getId().equals(cmnt.getId()));
        issueRepository.save(issue);
    }

}
