package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.ProgramDao;
import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.model.entities.MuscleGroup;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.SetGroup;
import org.avillar.gymtracker.services.LoginService;
import org.avillar.gymtracker.services.ProgramService;
import org.avillar.gymtracker.utils.VolumeCalculatorUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class ProgramServiceImpl implements ProgramService {
    private static final String NOT_FOUND_ERROR_MSG = "El programa no existe";
    private final ProgramDao programDao;
    private final ModelMapper modelMapper;
    private final LoginService loginService;
    private final VolumeCalculatorUtils volumeCalculatorUtils;

    /**
     * {@inheritDoc}
     */
    @Autowired
    public ProgramServiceImpl(ProgramDao programDao, ModelMapper modelMapper, LoginService loginService, VolumeCalculatorUtils volumeCalculatorUtils) {
        this.programDao = programDao;
        this.modelMapper = modelMapper;
        this.loginService = loginService;
        this.volumeCalculatorUtils = volumeCalculatorUtils;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProgramDto> getUserAllLoggedUserProgramsWithVolume() {
        final List<Program> programs = this.programDao.findByUserAppOrderByNameAsc(this.loginService.getLoggedUser());
        final List<ProgramDto> programDtos = new ArrayList<>(programs.size());

        for (final Program program : programs) {
            programDtos.add(this.volumeCalculatorUtils.calculateProgramVolume(program));
        }

        return programDtos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public ProgramDto getProgram(final Long programId) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programDao.findById(programId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(program);
        return this.modelMapper.map(program, ProgramDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ProgramDto createProgram(final ProgramDto programDto) {
        programDto.setId(null);
        final Program program = this.modelMapper.map(programDto, Program.class);
        program.setUserApp(this.loginService.getLoggedUser());
        return this.modelMapper.map(this.programDao.save(program), ProgramDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ProgramDto updateProgram(ProgramDto programDto) throws EntityNotFoundException, IllegalAccessException {
        final Program programDb = this.programDao.findById(programDto.getId()).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(programDb);
        final Program program = this.modelMapper.map(programDto, Program.class);
        program.setUserApp(programDb.getUserApp());
        return this.modelMapper.map(this.programDao.save(program), ProgramDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteProgram(final Long programId) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programDao.findById(programId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(program);
        this.programDao.deleteById(programId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public void programExistsAndIsFromLoggedUser(final Long programId) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programDao.findById(programId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(program);
    }

}
