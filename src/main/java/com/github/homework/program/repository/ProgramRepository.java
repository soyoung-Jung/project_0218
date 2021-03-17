package com.github.homework.program.repository;

import com.github.homework.program.domain.Program;
import com.github.homework.program.model.ProgramViewDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProgramRepository extends JpaRepository<Program, Long>, ProgramCustomRepository {
    Optional<Program> findByName(String name);
    List<Program> findTop10ByOrderByCountDesc();
}