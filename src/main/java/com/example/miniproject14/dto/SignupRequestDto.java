package com.example.miniproject14.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@Setter
@Getter
public class SignupRequestDto {


    private String username;

    private String password;

    private String nickname;

    private boolean admin = false;
    private String adminToken = "";
}