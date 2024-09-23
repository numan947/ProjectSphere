package com.numan947.pmbackend.primary_packages.comments;

import com.numan947.pmbackend.primary_packages.comments.dto.CommentRequest;
import com.numan947.pmbackend.primary_packages.comments.dto.CommentResponse;
import com.numan947.pmbackend.user.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentMapper {
    private final UserMapper userMapper;
    public CommentResponse toCommentResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .date(comment.getDate())
                .user(userMapper.toBriefUserResponse(comment.getUser()))
                .build();
    }

    public Comment toComment(CommentRequest commentRequest) {
        Comment c = Comment.builder()
                .content(commentRequest.content())
                .date(commentRequest.date())
                .build();
        if (commentRequest.commentId() != null) {
            c.setId(commentRequest.commentId());
        }
        return c;
    }
}
