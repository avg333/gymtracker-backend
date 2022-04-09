package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.Program;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProgramService {

    List<Program> getAllPrograms();

    Program getProgram(Long programId);

    Program createProgram(Program program);

    Program updateProgram(Long programId, Program program);

    void deleteProgram(Long programId);
}
