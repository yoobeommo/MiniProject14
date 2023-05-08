package com.example.miniproject14.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardRequestDto implements GeneralResponseDto{
    private Long id;
    private String type;
    private String title;
    private String date;
    private int memberNum;
    private int totalMember;
    private String contents;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
