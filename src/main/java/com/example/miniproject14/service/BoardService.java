package com.example.miniproject14.service;

import com.example.miniproject14.dto.BoardRequestDto;
import com.example.miniproject14.dto.BoardResponseDto;
import com.example.miniproject14.dto.GeneralResponseDto;
import com.example.miniproject14.dto.StatusResponseDto;
import com.example.miniproject14.entity.Board;
import com.example.miniproject14.entity.User;
import com.example.miniproject14.jwt.JwtUtil;
import com.example.miniproject14.repository.BoardRepository;
import com.example.miniproject14.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;


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

            // 게시물 조회 시 지원자 정보를 추가로 조회
            board.getApplicants().size(); // FetchType.LAZY로 인해 지원자 정보를 로드하기 위해 size() 메서드를 호출

            return new BoardResponseDto(board);
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
