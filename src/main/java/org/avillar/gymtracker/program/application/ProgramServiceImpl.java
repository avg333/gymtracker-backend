package org.avillar.gymtracker.program.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.program.domain.ProgramDao;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.avillar.gymtracker.utils.application.VolumeCalculatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long getAllUserProgramsSize(final Long userId) throws EntityNotFoundException, IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final Program program = new Program();
        program.setUserApp(userApp);
        this.authService.checkAccess(program);
        return this.programDao.countByUserApp(userApp);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<ProgramDto> getAllUserPrograms(final Long userId, final Pageable pageable) throws IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        final List<Program> programs = this.programDao.findByUserApp(userApp, pageable);
        final List<ProgramDto> programDtos = new ArrayList<>(programs.size());

        for (final Program program : programs) {
            this.authService.checkAccess(program);
            programDtos.add(this.volumeCalculatorUtils.calculateProgramVolume(program));
        }

        return programDtos;
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public ProgramDto getProgram(final Long programId) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programDao.findById(programId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(program);
        return this.volumeCalculatorUtils.calculateProgramVolume(program);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ProgramDto createProgram(final ProgramDto programDto) throws EntityNotFoundException, IllegalAccessException {
        final UserApp userApp = this.userDao.findById(programDto.getUserAppId()).orElseThrow(() ->
                new EntityNotFoundException(NOT_FOUND_ERROR_MSG));

        //TODO Validar programDto
        final Program program = this.modelMapper.map(programDto, Program.class);
        program.setUserApp(userApp);
        this.authService.checkAccess(program);
        return this.modelMapper.map(this.programDao.save(program), ProgramDto.class);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ProgramDto updateProgram(final ProgramDto programDto) throws EntityNotFoundException, IllegalAccessException {
        //TODO Validar programDto
        final Program programDb = this.programDao.findById(programDto.getId()).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(programDb);
        final Program program = this.modelMapper.map(programDto, Program.class);
        program.setUserApp(programDb.getUserApp());
        return this.modelMapper.map(this.programDao.save(program), ProgramDto.class);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteProgram(final Long programId) throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programDao.findById(programId).orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        this.authService.checkAccess(program);
        this.programDao.deleteById(programId);
    }
}