package org.avillar.gymtracker.services;

import org.avillar.gymtracker.exceptions.ResourceNotExistsException;
import org.avillar.gymtracker.model.dto.ProgramListDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.User;

import java.util.List;

public interface ProgramService {

    List<ProgramListDto> getUserAllPrograms(final User user);

    Program getProgram(Long programId) throws ResourceNotExistsException;

    Program createProgram(Program program);

    Program updateProgram(Long programId, Program program) throws ResourceNotExistsException;

    void deleteProgram(Long programId) throws ResourceNotExistsException;
}
