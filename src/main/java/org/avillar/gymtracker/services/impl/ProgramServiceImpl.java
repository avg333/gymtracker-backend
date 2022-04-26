package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.ProgramRepository;
import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.services.LoginService;
import org.avillar.gymtracker.services.ProgramService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProgramServiceImpl implements ProgramService {

    private static final String NOT_FOUND_ERROR_MSG = "El programa no existe";
    private static final String NO_PERMISSIONS = "El usuario logeado no tiene permisos para acceder al recurso";
    private final ProgramRepository programRepository;
    private final ModelMapper modelMapper;
    private final LoginService loginService;

    /**
     * {@inheritDoc}
     */
    @Autowired
    public ProgramServiceImpl(ProgramRepository programRepository, ModelMapper modelMapper, LoginService loginService) {
        this.programRepository = programRepository;
        this.modelMapper = modelMapper;
        this.loginService = loginService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProgramDto> getUserAllProgramsWithVolume() {
        final UserApp userApp = this.loginService.getLoggedUser();
        final List<Program> programs = this.programRepository.findByUserAppOrderByNameAsc(userApp);
        final List<ProgramDto> programDtos = new ArrayList<>(programs.size());

        for (final Program program : programs) {
            final ProgramDto programDto = this.modelMapper.map(program, ProgramDto.class);
            programDto.setSessionNumber(program.getSessions().size());
            programDtos.add(programDto);
        }

        return programDtos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public ProgramDto getProgram(final Long programId) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programRepository.findById(programId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.perteneceAlUsuarioLogeado(program);
        return this.modelMapper.map(program, ProgramDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ProgramDto createProgram(final ProgramDto programDto) {
        if (programDto.getId() != null && this.programRepository.existsById(programDto.getId())) {
            programDto.setId(null);
        }
        final Program program = this.modelMapper.map(programDto, Program.class);
        program.setUserApp(this.loginService.getLoggedUser());
        return this.modelMapper.map(this.programRepository.save(program), ProgramDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public ProgramDto updateProgram(ProgramDto programDto) throws EntityNotFoundException, IllegalAccessException {
        final Program programDb;

        if (programDto.getId() == null) {
            throw new EntityNotFoundException(NOT_FOUND_ERROR_MSG);
        } else {
            programDb = this.programRepository.findById(programDto.getId()).orElse(null);
            if (programDb == null) {
                throw new EntityNotFoundException(NOT_FOUND_ERROR_MSG);
            }
        }
        this.perteneceAlUsuarioLogeado(programDb);
        final Program program = this.modelMapper.map(programDto, Program.class);
        program.setUserApp(programDb.getUserApp());
        program.setSessions(programDb.getSessions());
        //TODO Deberia actualizarse sin pisar los valores viejos de usuario y sesiones
        return this.modelMapper.map(this.programRepository.save(program), ProgramDto.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteProgram(final Long programId) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programRepository.findById(programId).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.perteneceAlUsuarioLogeado(program);
        this.programRepository.deleteById(programId);
    }

    private void perteneceAlUsuarioLogeado(final Program program) throws IllegalAccessException {
        if (program.getUserApp() != null && !program.getUserApp().getId().equals(this.loginService.getLoggedUser().getId())) {
            throw new IllegalAccessException(NO_PERMISSIONS);
        }
    }
}
