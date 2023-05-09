package com.example.miniproject14.controller;

import com.example.miniproject14.dto.GeneralResponseDto;
import com.example.miniproject14.dto.LoginRequestDto;
import com.example.miniproject14.dto.SignupRequestDto;
import com.example.miniproject14.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController // @Controller 어노테이션은 html을 반환하기 때문에 RestController를 사용함
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;



    @PostMapping("/signup")
    public ResponseEntity<GeneralResponseDto> signup(
            @RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok().body(userService.signup(signupRequestDto));
    }


    @PostMapping("/login")
    public ResponseEntity<GeneralResponseDto > login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response) {
        return ResponseEntity.ok().body(userService.login(loginRequestDto, response));
    }
}

