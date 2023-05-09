package com.example.miniproject14.dto;

import com.example.miniproject14.entity.Board;
import com.example.miniproject14.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class ApplicantsRequestDto {
    private Long boardId; // board 객체 대신 boardId 필드를 사용

    private User user;

}
