package com.example.miniproject14.entity;

import com.example.miniproject14.dto.ApplicantsRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Applicants extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicants_id")
    private Long id;


    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    @ManyToOne
    @JoinColumn(name = "member_id")
    private User user;


    public Applicants(Board board, User user) {
        this.board = board;
        this.user = user;


    }
}
