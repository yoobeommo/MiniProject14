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
    @GetMapping("/boards/{id}")
    public GeneralResponseDto getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    @PutMapping("/boards/{id}")
    public GeneralResponseDto updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails);
    }

    @DeleteMapping("/boards/{id}")
    public StatusResponseDto deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.deleteBoard(id, userDetails);
    }
}
