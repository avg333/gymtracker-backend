package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.dao.ProgramRepository;
import org.avillar.gymtracker.model.Program;
import org.avillar.gymtracker.services.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public List<Program> getAllPrograms() {
        return this.programRepository.findAllByOrderByNameAsc();
    }

    @Override
    public Program getProgram(Long programId) {
        return this.programRepository.findById(programId).orElse(null);
    }

    @Override
    public Program createProgram(Program program) {
        return this.programRepository.save(program);
    }

    @Override
    public Program updateProgram(Long programId, Program program) {
        program.setId(programId);
        return this.programRepository.save(program);
    }

    @Override
    public void deleteProgram(Long programId) {
        this.programRepository.deleteById(programId);
    }
}
