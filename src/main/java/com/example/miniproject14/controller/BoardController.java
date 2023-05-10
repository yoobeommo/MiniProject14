package com.example.miniproject14.controller;


import com.example.miniproject14.dto.BoardRequestDto;
import com.example.miniproject14.dto.BoardResponseDto;
import com.example.miniproject14.dto.GeneralResponseDto;
import com.example.miniproject14.dto.StatusResponseDto;
import com.example.miniproject14.security.UserDetailsImpl;
import com.example.miniproject14.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // 컨트롤러에서 설정
public class BoardController {

    private final BoardService boardService;

    //Create
    @PostMapping("/boards")
    public GeneralResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.createBoard(requestDto, userDetails);
    }

    //ReadAll
    @GetMapping("/boards")
    public List<BoardResponseDto> getAllBoards(){
        return boardService.getAllBoards();
    }

    //ReadByID
    @GetMapping("/boards/{boardId}")
    public GeneralResponseDto getBoard(@PathVariable Long boardId){
        return boardService.getBoard(boardId);
    }

    @PutMapping("/boards/{boardId}")
    public GeneralResponseDto updateBoard(@PathVariable Long boardId, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(boardId, requestDto, userDetails);
    }

    @DeleteMapping("/boards/{boardId}")
    public StatusResponseDto deleteBoard(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.deleteBoard(boardId, userDetails);
    }
}
