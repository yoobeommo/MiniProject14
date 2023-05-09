package com.example.miniproject14.service;

import com.example.miniproject14.dto.*;
import com.example.miniproject14.entity.Board;
import com.example.miniproject14.entity.Comment;
import com.example.miniproject14.entity.User;
import com.example.miniproject14.jwt.JwtUtil;
import com.example.miniproject14.repository.BoardRepository;
import com.example.miniproject14.repository.CommentRepository;
import com.example.miniproject14.repository.UserRepository;
import com.example.miniproject14.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;



    // 댓글 작성 메서드
    @Transactional
    public StatusResponseDto createComment(CommentRequestDto requestDto, UserDetailsImpl userDetails){
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")   // 예외처리 방법이나 메세지 수정
        );
        Comment comment = new Comment(requestDto, board, userDetails.getUser());
        commentRepository.save(comment);
        return new StatusResponseDto("댓글 작성완료", HttpStatus.OK); // test 를 위해 "댓글 작성완료" 로 출력
    }

    //댓글 수정 메서드
    @Transactional
    public StatusResponseDto updateComment(Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails){
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(
                () -> new NullPointerException("존재하지 않는 게시글입니다.")   // NullPointerException 으로 처리하는 오류들이 처리되지 않음
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("존재하지 않는 댓글입니다.")
        );
        // User 의 username 필드는 (아마 loginid) uniqe 값이기 때문에 같은 사용자인지 판별하기 위해 사용 :: userDetails.impl 에서 id 를 반환하는 메서드가 있으면 좋을 듯
        if(comment.getUser().getUsername().equals(userDetails.getUsername())){
            comment.update(requestDto);
            return new StatusResponseDto("댓글 수정 완료", HttpStatus.OK);
        }
        return new StatusResponseDto("직접 작성한 댓글만 수정할 수 있습니다.",HttpStatus.BAD_REQUEST);
    }

    //댓글 삭제 메서드
    @Transactional
    public StatusResponseDto deleteComment(Long commentId, UserDetailsImpl userDetails){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () ->    new NullPointerException("존재하지 않는 댓글입니다.")
        );
        if(comment.getUser().getUsername().equals(userDetails.getUsername())){
            commentRepository.delete(comment);
            return new StatusResponseDto("댓글 삭제 완료", HttpStatus.OK);
        }
        return new StatusResponseDto("직접 작성한 게시글만 삭제할 수 있습니다.",HttpStatus.BAD_REQUEST);
    }

}