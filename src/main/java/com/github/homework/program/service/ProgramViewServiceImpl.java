package com.github.homework.program.service;

import com.github.homework.program.domain.Program;
import com.github.homework.program.exception.ProgramNotFoundException;
import com.github.homework.program.model.ProgramViewDto;
import com.github.homework.program.repository.ProgramRepository;
import com.github.homework.theme.domain.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProgramViewServiceImpl implements ProgramViewService {

    private final ProgramRepository programRepository;

    @Override
    public Optional<ProgramViewDto> getBy(Long id) {
        Optional<Program> byId = programRepository.findById(id);
        if (byId.isPresent()){
            Program entity = byId.get();
            entity.updateCount();
        }
        return byId.map(p ->
                new ProgramViewDto(
                        p.getId(),
                        p.getName(),
                        p.getIntroduction(),
                        p.getIntroductionDetail(),
                        p.getRegion(),
                        p.getTheme().getName(),
                        p.getCount()
                )
        );
    }

    @Override
    public Page<ProgramViewDto> pageBy(Pageable pageable) {
        return programRepository.findBy(pageable);
    }

    @Override
    public Optional<ProgramViewDto> findByName(String name) {
        Optional<Program> byName = programRepository.findByName(name);
//        if (byName.isPresent()){
//            Program entity = byName.get();
//            entity.setCount(entity.getCount() + 1);
//            programRepository.save(entity);
        return byName.map(p ->
                new ProgramViewDto(
                        p.getId(),
                        p.getName(),
                        p.getIntroduction(),
                        p.getIntroductionDetail(),
                        p.getRegion(),
                        p.getTheme().getName(),
                        p.getCount()
                )
        );
    }

    @Override
    public List<ProgramViewDto> findTop10ByCount() {
        List<Program> programs = programRepository.findTop10ByOrderByCountDesc();
        return programs.stream().map(p ->
                new ProgramViewDto(
                        p.getId(),
                        p.getName(),
                        p.getIntroduction(),
                        p.getIntroductionDetail(),
                        p.getRegion(),
                        p.getTheme().getName(),
                        p.getCount()
                )).collect(Collectors.toList());
    }
}
