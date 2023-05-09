package com.example.miniproject14.dto;

import com.example.miniproject14.entity.Board;
import com.example.miniproject14.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDto implements GeneralResponseDto{
    private Long id;
    private String type;
    private String title;
    private String date;
    private int totalMember;
    private int memberNum;
    private String contents;
    private String nickname;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<String> applyUsers;
    private List<Long> applyUserId;
    private List<CommentResponseDto> commentList;


    public BoardResponseDto(Board board){
        this.id = board.getId();
        this.type =board.getType();
        this.title = board.getTitle();
        this.date= board.getDate();
        this.totalMember= board.getTotalMember();
        this.contents=board.getContents();
        this.nickname= board.getNickname();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.applyUsers = board.getApplicants().stream()
                .map(applicant -> applicant.getUser().getNickname())
                .collect(Collectors.toList());
        this.memberNum = this.applyUsers.size();
    }

    public BoardResponseDto(Board board, List<CommentResponseDto> commentResponseDtoList){
        this.id = board.getId();
        this.type =board.getType();
        this.title = board.getTitle();
        this.date= board.getDate();
        this.totalMember= board.getTotalMember();
        this.contents=board.getContents();
        this.nickname= board.getNickname();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.applyUsers = board.getApplicants().stream()
                .map(applicant -> applicant.getUser().getNickname())
                .collect(Collectors.toList());
        this.memberNum = this.applyUsers.size();
        this.applyUserId=board.getApplicants().stream()
                .map(applicant -> applicant.getUser().getId())
                .collect(Collectors.toList());
        this.commentList = commentResponseDtoList;
    }
}

