package com.numan947.pmbackend.primary_packages.comments;

import com.numan947.pmbackend.exception.OperationNotPermittedException;
import com.numan947.pmbackend.primary_packages.comments.dto.CommentRequest;
import com.numan947.pmbackend.primary_packages.comments.dto.CommentResponse;
import com.numan947.pmbackend.primary_packages.issue.Issue;
import com.numan947.pmbackend.primary_packages.issue.IssueService;
import com.numan947.pmbackend.primary_packages.project.ProjectService;
import com.numan947.pmbackend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final IssueService issueService;
    private final ProjectService projectService;

    @Override
    public CommentResponse addComment(CommentRequest commentRequest, Authentication auth) {
        // validation:
        // 1. Check if the user is authenticated
        // 2. Check if the user is a member of the project
        // 3. Check if the issue exists
        User user = (User) auth.getPrincipal();
        if(!projectService.isUserPartOfProject(user.getId(), commentRequest.projectId())){
            throw new OperationNotPermittedException("User is not part of the project");
        }
        Issue issue = issueService.getIssueByProjectIdAndIssueId(commentRequest.projectId(), commentRequest.issueId())
                .orElseThrow(
                        () -> new OperationNotPermittedException("Issue does not exist")
                );
        // create the comment
        Comment cmnt = commentMapper.toComment(commentRequest);
        cmnt.setUser(user);
        cmnt.setIssue(issue);
        cmnt = commentRepository.save(cmnt);
        //update issue
        issueService.addCommentToIssue(issue.getId(), cmnt);
        return commentMapper.toCommentResponse(cmnt);
    }

    @Override
    public void deleteComment(String projectId, String issueId, String commentId, Authentication auth) {
        // validation:
        // 1. Check if the user is authenticated
        // 2. Check if the user is a member of the project
        // 3. Check if the issue exists
        // 4. Check if the comment exists
        // 5. Check if the user is the owner of the comment or the owner of the project
        User user = (User) auth.getPrincipal();
        if(!projectService.isUserPartOfProject(user.getId(), projectId)){
            throw new OperationNotPermittedException("User is not part of the project");
        }
        Issue issue = issueService.getIssueByProjectIdAndIssueId(projectId, issueId)
                .orElseThrow(
                        () -> new OperationNotPermittedException("Issue does not exist")
                );
        Comment cmnt = commentRepository.findByIssueIdAndCommentId(issueId, commentId).orElseThrow(
                () -> new OperationNotPermittedException("Comment does not exist")
        );
        if(!cmnt.getUser().getId().equals(user.getId()) && !issue.getProject().getOwner().getId().equals(user.getId())){
            throw new OperationNotPermittedException("User is not the owner of the comment or the project");
        }

        // delete the comment
        commentRepository.delete(cmnt);
        // update issue
        issueService.removeCommentFromIssue(issue.getId(), cmnt);
    }

    @Override
    public List<CommentResponse> getComments(String projectId, String issueId, Authentication auth) {
        // validation:
        // 1. Check if the user is authenticated
        // 2. Check if the user is a member of the project
        // 3. Check if the issue exists
        User user = (User) auth.getPrincipal();
        if(!projectService.isUserPartOfProject(user.getId(), projectId)){
            throw new OperationNotPermittedException("User is not part of the project");
        }
        Issue issue = issueService.getIssueByProjectIdAndIssueId(projectId, issueId)
                .orElseThrow(
                        () -> new OperationNotPermittedException("Issue does not exist")
                );
        return commentRepository.findAllByIssueId(issueId).stream()
                .map(commentMapper::toCommentResponse)
                .toList();
    }

    @Override
    public CommentResponse editComment(CommentRequest commentRequest, Authentication auth) {
        // validation:
        // 1. Check if the user is authenticated
        // 2. Check if the user is a member of the project
        // 3. Check if the issue exists
        // 4. Check if the comment exists
        // 5. Check if the user is the owner of the comment
        if (commentRequest.commentId() == null || commentRequest.commentId().isEmpty()) {
            throw new OperationNotPermittedException("Comment id cannot be null or empty");
        }
        User user = (User) auth.getPrincipal();
        if(!projectService.isUserPartOfProject(user.getId(), commentRequest.projectId())){
            throw new OperationNotPermittedException("User is not part of the project");
        }
        Issue issue = issueService.getIssueByProjectIdAndIssueId(commentRequest.projectId(), commentRequest.issueId())
                .orElseThrow(
                        () -> new OperationNotPermittedException("Issue does not exist")
                );
        Comment cmnt = commentRepository.findByIssueIdAndCommentId(commentRequest.issueId(), commentRequest.commentId()).orElseThrow(
                () -> new OperationNotPermittedException("Comment does not exist")
        );
        if(!cmnt.getUser().getId().equals(user.getId())){
            throw new OperationNotPermittedException("User is not the owner of the comment");
        }

        // remove the comment from the issue
        issueService.removeCommentFromIssue(issue.getId(), cmnt);

        // update the comment
        Comment updated = commentMapper.toComment(commentRequest);
        cmnt.setContent(updated.getContent());
        cmnt.setDate(updated.getDate());
        cmnt = commentRepository.save(cmnt);
        // add the comment to the issue
        issueService.addCommentToIssue(issue.getId(), cmnt);
        return commentMapper.toCommentResponse(cmnt);
    }

}
