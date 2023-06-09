package com.example.miniproject14.controller;

import com.example.miniproject14.dto.GeneralResponseDto;
import com.example.miniproject14.dto.LoginRequestDto;
import com.example.miniproject14.dto.SignupRequestDto;
import com.example.miniproject14.dto.StatusResponseDto;
import com.example.miniproject14.entity.User;
import com.example.miniproject14.security.UserDetailsImpl;
import com.example.miniproject14.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController // @Controller 어노테이션은 html을 반환하기 때문에 RestController를 사용함
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;



    @PostMapping("/signup")
    public GeneralResponseDto signup(@RequestBody SignupRequestDto signupRequestDto ) {
        try {
            return userService.signup(signupRequestDto);
        } catch (Exception e) {
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);                         // 예외 발생시 에러 내용, Httpstatus(400)을 리턴값으로 전달한다.
        }

    }

    @ResponseBody
    @PostMapping("/login")
    public GeneralResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        try {
            return userService.login(loginRequestDto, response);
        } catch (Exception e) {
            return new StatusResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST);                         // 예외 발생시 에러 내용, Httpstatus(400)을 리턴값으로 전달한다.
        }
    }
}

