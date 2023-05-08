package com.example.miniproject14.controller;

import com.example.miniproject14.dto.LoginRequestDto;
import com.example.miniproject14.dto.ResponseDto;
import com.example.miniproject14.dto.SignupRequestDto;
import com.example.miniproject14.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController // @Controller 어노테이션은 html을 반환하기 때문에 RestController를 사용함
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;



    @PostMapping("/signup")
    public ResponseEntity<ResponseDto> signup(
            @RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok().body(userService.signup(signupRequestDto));
    }


    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response) {
        return ResponseEntity.ok().body(userService.login(loginRequestDto, response));
    }
}

