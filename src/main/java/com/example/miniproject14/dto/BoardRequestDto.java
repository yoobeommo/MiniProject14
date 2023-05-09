package com.example.miniproject14.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto {

    private String type;
    private String title;
    private String date;
    private int totalMember;
    private String contents;
    private String nickname;


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
