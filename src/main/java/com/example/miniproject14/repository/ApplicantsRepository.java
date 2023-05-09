package com.example.miniproject14.repository;

import com.example.miniproject14.entity.Applicants;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicantsRepository extends JpaRepository<Applicants, Long> {
    void deleteByUserIdAndBoardId(Long userId, Long boardId);
}
