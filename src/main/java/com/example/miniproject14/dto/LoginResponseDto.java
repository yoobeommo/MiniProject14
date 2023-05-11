package com.example.miniproject14.dto;
import com.example.miniproject14.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto implements GeneralResponseDto{
    private Long userid;
    public LoginResponseDto(User user) {
        this.userid = user.getId();
    }
}

