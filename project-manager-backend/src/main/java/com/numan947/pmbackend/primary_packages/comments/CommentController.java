package com.numan947.pmbackend.primary_packages.comments;

import com.numan947.pmbackend.primary_packages.comments.dto.CommentRequest;
import com.numan947.pmbackend.primary_packages.comments.dto.CommentResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
@Tag(name = "Comments", description = "Comments API")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<CommentResponse>addComment( // tested and working
            @RequestBody @Valid CommentRequest commentRequest,
            Authentication auth
    ){
        return ResponseEntity.ok(commentService.addComment(commentRequest, auth));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?>deleteComment( // tested and working
            @RequestParam("project-id")  @NotNull @NotEmpty @NotBlank @Valid String projectId,
            @RequestParam("issue-id") @NotNull @NotEmpty @NotBlank @Valid String issueId,
            @RequestParam("comment-id") @NotNull @NotEmpty @NotBlank @Valid String commentId,
            Authentication auth
    ){
        //TODO: Only the owner of the comment or the project owner can delete the comment
        commentService.deleteComment(projectId, issueId, commentId, auth);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get")
    public ResponseEntity<List<CommentResponse>>getComment(
            @RequestParam("project-id") @NotNull @NotEmpty @NotBlank @Valid String projectId,
            @RequestParam("issue-id") @NotNull @NotEmpty @NotBlank @Valid String issueId,
            Authentication auth
    ){
        return ResponseEntity.ok(commentService.getComments(projectId, issueId, auth));
    }


    @PutMapping("/edit")
    public ResponseEntity<CommentResponse>editComment(
            @RequestBody @Valid CommentRequest commentRequest,
            Authentication auth
    ){
        return ResponseEntity.ok(commentService.editComment(commentRequest, auth));
    }
}
