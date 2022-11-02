package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.ProgramDao;
import org.avillar.gymtracker.model.dao.UserDao;
import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.services.ProgramService;
import org.avillar.gymtracker.utils.VolumeCalculatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgramServiceImpl extends BaseService implements ProgramService {
    private static final String NOT_FOUND_ERROR_MSG = "El programa no existe";
    private final ProgramDao programDao;
    private final UserDao userDao;
    private final VolumeCalculatorUtils volumeCalculatorUtils;

    @Autowired
    public ProgramServiceImpl(ProgramDao programDao, UserDao userDao, VolumeCalculatorUtils volumeCalculatorUtils) {
        this.programDao = programDao;
        this.volumeCalculatorUtils = volumeCalculatorUtils;
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgramDto> getAllUserPrograms(final Long userId) throws IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final List<Program> programs = this.programDao.findByUserAppOrderByNameAsc(userApp);
        final List<ProgramDto> programDtos = new ArrayList<>(programs.size());

        for (final Program program : programs) {
            this.loginService.checkAccess(program);
            programDtos.add(this.volumeCalculatorUtils.calculateProgramVolume(program));
        }

        return programDtos;
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramDto getProgram(final Long programId) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programDao.findById(programId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(program);
        return this.modelMapper.map(program, ProgramDto.class);
    }

    @Override
    @Transactional
    public ProgramDto createProgram(final ProgramDto programDto) {
        final Program program = this.modelMapper.map(programDto, Program.class);
        program.setUserApp(this.loginService.getLoggedUser());
        return this.modelMapper.map(this.programDao.save(program), ProgramDto.class);
    }

    @Override
    @Transactional
    public ProgramDto updateProgram(final ProgramDto programDto) throws EntityNotFoundException, IllegalAccessException {
        final Program programDb = this.programDao.findById(programDto.getId()).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(programDb);
        final Program program = this.modelMapper.map(programDto, Program.class);
        program.setUserApp(programDb.getUserApp());
        return this.modelMapper.map(this.programDao.save(program), ProgramDto.class);
    }

    @Override
    @Transactional
    public void deleteProgram(final Long programId) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programDao.findById(programId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.loginService.checkAccess(program);
        this.programDao.deleteById(programId);
    }
}