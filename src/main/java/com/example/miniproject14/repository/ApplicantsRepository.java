package com.example.miniproject14.repository;

import com.example.miniproject14.entity.Applicants;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicantsRepository extends JpaRepository<Applicants, Long> {
    void deleteByUserIdAndBoardId(Long userId, Long boardId);
    Optional<Applicants> findByUserIdAndBoardId(Long userId, Long boardId);

}
