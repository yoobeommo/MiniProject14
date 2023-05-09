package com.example.miniproject14.entity;

import com.example.miniproject14.dto.CommentRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Comment extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    @ManyToOne
    @JoinColumn(name = "member_id")  //user_id로
    private User user;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String contents;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime modifiedAt;


    public Comment(CommentRequestDto requestDto, Board board, User user){
        this.board = board;
        this.user = user;
        this.contents = requestDto.getContents();
        this.nickname = user.getNickname();
    }

    public void update(CommentRequestDto requestDto){
        this.contents = requestDto.getContents();
//        this.nickname = user.getNickname();
    }
}
