package com.example.miniproject14.controller;

import com.example.miniproject14.dto.ApplicantsRequestDto;
import com.example.miniproject14.dto.GeneralResponseDto;
import com.example.miniproject14.dto.ResponseDto;
import com.example.miniproject14.entity.Board;
import com.example.miniproject14.security.UserDetailsImpl;
import com.example.miniproject14.service.ApplicantsService;
import com.example.miniproject14.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController // @Controller 어노테이션은 html을 반환하기 때문에 RestController를 사용함
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // 컨트롤러에서 설정
public class ApplicantsController {

    private final ApplicantsService applicantsService;
    private final BoardService boardService;

    @ResponseBody
    @PostMapping("/applicants")
    public GeneralResponseDto AddApplicants(@RequestBody ApplicantsRequestDto applicantsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            Board board = boardService.findBoardById(applicantsRequestDto.getBoardId()); // getBoard() 대신 getBoardId()를 사용
            return applicantsService.addApplicants(board, userDetails.getUser());
        } catch (Exception e) {
            return new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()); // 예외 발생시 에러 내용, Httpstatus(400)을 리턴값으로 전달한다.
        }
    }

    @ResponseBody
    @DeleteMapping("/applicants")
    public GeneralResponseDto DeleteApplicants(@RequestBody ApplicantsRequestDto applicantsRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        try {
            return applicantsService.deleteApplicants(applicantsRequestDto, userDetails.getUser());
        } catch (Exception e) {
            return new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()); // 예외 발생시 에러 내용, Httpstatus(400)을 리턴값으로 전달한다.!
        }
    }
}
