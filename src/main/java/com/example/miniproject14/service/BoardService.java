package com.example.miniproject14.service;

import com.example.miniproject14.dto.*;
import com.example.miniproject14.entity.Board;
import com.example.miniproject14.entity.Comment;
import com.example.miniproject14.entity.User;
import com.example.miniproject14.repository.BoardRepository;
import com.example.miniproject14.repository.CommentRepository;
import com.example.miniproject14.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;


    @Transactional
    public GeneralResponseDto createBoard(BoardRequestDto requestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        requestDto.setNickname(user.getNickname());
        Board board = new Board(requestDto);
        board.setUser(user);
        boardRepository.save(board);

        return new StatusResponseDto("작성완료", HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public List<BoardResponseDto> getAllBoards(){
        List<BoardResponseDto> AllBoards = new ArrayList<>();
        List<Board> BoardList = boardRepository.findAllByOrderByModifiedAtDesc();
        for(Board board : BoardList) {
            AllBoards.add(new BoardResponseDto(board));
        }
        return AllBoards;
    }

    @Transactional(readOnly = true)
    public GeneralResponseDto getBoard(Long id){

        try {
            Board board = findBoardById(id);
            List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
            List<Comment> commentList = commentRepository.findAllByboard_id(id);

            for(Comment comment : commentList) {
                commentResponseDtoList.add(new CommentResponseDto(comment));
            }

            board.getApplicants().size();

            return new BoardResponseDto(board, commentResponseDtoList);
        } catch (NullPointerException e) {
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Transactional
    public GeneralResponseDto updateBoard(Long id, BoardRequestDto requestDto, UserDetailsImpl userDetails){
        try{
            Board board = findBoardById(id);

            if (board.getUser().getUsername().equals(userDetails.getUsername())) {
                board.update(requestDto);

                return new StatusResponseDto("수정 완료", HttpStatus.OK);
            }
            return new StatusResponseDto("직접 작성한 게시글만 수정할 수 있습니다.",HttpStatus.BAD_REQUEST);
        }catch(NullPointerException e){
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public Board findBoardById(Long id) throws NullPointerException{
        return boardRepository.findById(id).orElseThrow(
                ()-> new NullPointerException("존재하지 않는 게시글입니다.")
        );
    }

    @Transactional
    public StatusResponseDto deleteBoard(Long id, UserDetailsImpl userDetails){
        try {
            Board board = findBoardById(id);
            if (board.getUser().getUsername().equals(userDetails.getUsername())) {
                boardRepository.delete(board);
                return new StatusResponseDto("삭제완료", HttpStatus.OK);
            }
            return new StatusResponseDto("직접 작성한 게시글만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST);
        }catch (NullPointerException e){
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);

        }
    }



}
