package com.example.miniproject14.controller;


import com.example.miniproject14.dto.*;
import com.example.miniproject14.security.UserDetailsImpl;
import com.example.miniproject14.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // 컨트롤러에서 설정
public class CommentController {

    private final CommentService commentService;

    // CommentCreate
    @PostMapping("/boards/comments")
    public StatusResponseDto createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.createComment(commentRequestDto, userDetails);
    }

    // CommentUpdate
    @PutMapping("/boards/comments/{commentId}")
    public StatusResponseDto updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(commentId, requestDto, userDetails);
    }

    // CommentDelete
    @DeleteMapping("/boards/comments/{commentId}")
    public StatusResponseDto deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.deleteComment(commentId, userDetails);
    }

}
