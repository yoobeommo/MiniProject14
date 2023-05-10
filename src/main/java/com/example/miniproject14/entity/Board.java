package com.example.miniproject14.entity;

import com.example.miniproject14.dto.BoardRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private  int totalMember;

    @Column(nullable = false)
    private int memberNum;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private String nickname;

    @ManyToOne
    @JoinColumn
    private User user;


    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Applicants> applicants = new ArrayList<>();

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();


    public int getMemberNum() {
        return applicants.size();
    }

    public Board(BoardRequestDto requestDto) {
        this.type = requestDto.getType();
        this.title = requestDto.getTitle();
        this.date = requestDto.getDate();
        this.totalMember = requestDto.getTotalMember();
        this.contents = requestDto.getContents();
        this.nickname = requestDto.getNickname();
    }

    public void update(BoardRequestDto requestDto){
        this.type = requestDto.getType();
        this.title = requestDto.getTitle();
        this.date = requestDto.getDate();
        this.totalMember = requestDto.getTotalMember();
        this.contents = requestDto.getContents();
    }
}
