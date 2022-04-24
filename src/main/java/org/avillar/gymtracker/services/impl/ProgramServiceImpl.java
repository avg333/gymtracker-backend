package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.exceptions.ResourceNotExistsException;
import org.avillar.gymtracker.model.dao.ProgramRepository;
import org.avillar.gymtracker.model.dto.ProgramListDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.User;
import org.avillar.gymtracker.services.ProgramService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository, ModelMapper modelMapper) {
        this.programRepository = programRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgramListDto> getUserAllPrograms(final User user) {
        final List<Program> programs = this.programRepository.findByUserOrderByNameAsc(user);
        final List<ProgramListDto> programListDtos = new ArrayList<>(programs.size());

        for (final Program program : programs) {
            final ProgramListDto programListDto = this.modelMapper.map(program, ProgramListDto.class);
            programListDto.setSessionNumber(program.getSessions().size());
            programListDtos.add(programListDto);
        }

        return programListDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public Program getProgram(final Long programId) throws ResourceNotExistsException {
        return this.programRepository.findById(programId).orElseThrow(() -> new ResourceNotExistsException("No existe el programa"));
    }

    @Override
    @Transactional
    public Program createProgram(final Program program) {
        program.setId(null);
        return this.programRepository.save(program);
    }

    @Override
    @Transactional
    public Program updateProgram(Long programId, Program program) throws ResourceNotExistsException {
        if (!this.programRepository.existsById(programId)) {
            throw new ResourceNotExistsException("El programa no existe");
        }

        program.setId(programId);
        return this.programRepository.save(program);
    }

    @Override
    @Transactional
    public void deleteProgram(Long programId) throws ResourceNotExistsException {
        if (!this.programRepository.existsById(programId)) {
            throw new ResourceNotExistsException("El programa no existe");
        }
        this.programRepository.deleteById(programId);
    }
}
