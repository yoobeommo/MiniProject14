package com.example.miniproject14.dto;

import com.example.miniproject14.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private Long userId;
    private String nickname;
    private String contents;
    private LocalDateTime createdAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.userId = comment.getUser().getId();
        this.nickname = comment.getNickname();
        this.contents = comment.getContents();
        this.createdAt = comment.getCreatedAt();
    }
}
