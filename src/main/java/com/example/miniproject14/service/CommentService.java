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



    // ��� �ۼ� �޼���
    @Transactional
    public StatusResponseDto createComment(CommentRequestDto requestDto, UserDetailsImpl userDetails){
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(
                () -> new NullPointerException("�������� �ʴ� �Խñ��Դϴ�.")   // ����ó�� ����̳� �޼��� ����
        );
        Comment comment = new Comment(requestDto, board, userDetails.getUser());
        commentRepository.save(comment);
        return new StatusResponseDto("��� �ۼ��Ϸ�", HttpStatus.OK); // test �� ���� "��� �ۼ��Ϸ�" �� ���
    }

    //��� ���� �޼���
    @Transactional
    public StatusResponseDto updateComment(Long commentId, CommentRequestDto requestDto, UserDetailsImpl userDetails){
        Board board = boardRepository.findById(requestDto.getBoardId()).orElseThrow(
                () -> new NullPointerException("�������� �ʴ� �Խñ��Դϴ�.")   // NullPointerException ���� ó���ϴ� �������� ó������ ����
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NullPointerException("�������� �ʴ� ����Դϴ�.")
        );
        // User �� username �ʵ�� (�Ƹ� loginid) uniqe ���̱� ������ ���� ��������� �Ǻ��ϱ� ���� ��� :: userDetails.impl ���� id �� ��ȯ�ϴ� �޼��尡 ������ ���� ��
        if(comment.getUser().getUsername().equals(userDetails.getUsername())){
            comment.update(requestDto);
            return new StatusResponseDto("��� ���� �Ϸ�", HttpStatus.OK);
        }
        return new StatusResponseDto("���� �ۼ��� ��۸� ������ �� �ֽ��ϴ�.",HttpStatus.BAD_REQUEST);
    }

    //��� ���� �޼���
    @Transactional
    public StatusResponseDto deleteComment(Long commentId, UserDetailsImpl userDetails){
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () ->    new NullPointerException("�������� �ʴ� ����Դϴ�.")
        );
        if(comment.getUser().getUsername().equals(userDetails.getUsername())){
            commentRepository.delete(comment);
            return new StatusResponseDto("��� ���� �Ϸ�", HttpStatus.OK);
        }
        return new StatusResponseDto("���� �ۼ��� �Խñ۸� ������ �� �ֽ��ϴ�.",HttpStatus.BAD_REQUEST);
    }

}
