package com.numan947.pmbackend.primary_packages.comments;

import com.numan947.pmbackend.primary_packages.comments.dto.CommentResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, String> {
    @Query("SELECT c FROM Comment c WHERE c.issue.id = :issueId AND c.id = :commentId")
    Optional<Comment> findByIssueIdAndCommentId(String issueId, String commentId);

    @Query("SELECT c FROM Comment c WHERE c.issue.id = :issueId")
    List<Comment> findAllByIssueId(String issueId);
}
