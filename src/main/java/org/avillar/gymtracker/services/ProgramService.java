package org.avillar.gymtracker.services;

import org.avillar.gymtracker.exceptions.ResourceNotExistsException;
import org.avillar.gymtracker.model.entities.Program;

import java.util.List;

public interface ProgramService {

    List<Program> getAllPrograms();

    Program getProgram(Long programId) throws ResourceNotExistsException;

    Program createProgram(Program program);

    Program updateProgram(Long programId, Program program) throws ResourceNotExistsException;

    void deleteProgram(Long programId) throws ResourceNotExistsException;
}
