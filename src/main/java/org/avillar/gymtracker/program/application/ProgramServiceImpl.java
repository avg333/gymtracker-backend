package org.avillar.gymtracker.program.application;

import org.avillar.gymtracker.base.application.BaseService;
import org.avillar.gymtracker.errors.application.exceptions.BadFormException;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.program.application.dto.ProgramDto;
import org.avillar.gymtracker.program.application.dto.ProgramDtoValidator;
import org.avillar.gymtracker.program.domain.Program;
import org.avillar.gymtracker.program.domain.ProgramDao;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.avillar.gymtracker.utils.application.VolumeCalculatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.DataBinder;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProgramServiceImpl extends BaseService implements ProgramService {

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
    public long getAllUserProgramsSize(final Long userId)
            throws EntityNotFoundException, IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserApp.class, userId));

        final Program program = new Program();
        program.setUserApp(userApp);
        this.authService.checkAccess(program);

        return this.programDao.countByUserApp(userApp);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public List<ProgramDto> getAllUserPrograms(final Long userId, final Pageable pageable)
            throws EntityNotFoundException, IllegalAccessException {
        final UserApp userApp = this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(UserApp.class, userId));

        final Program program = new Program();
        program.setUserApp(userApp);
        this.authService.checkAccess(program);

        return this.programDao.findByUserApp(userApp, pageable)
                .stream()
                .map(programAux -> this.volumeCalculatorUtils.calculateProgramVolume(program))
                .toList();
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    public ProgramDto getProgram(final Long programId)
            throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programDao.findById(programId)
                .orElseThrow(() -> new EntityNotFoundException(Program.class, programId));
        this.authService.checkAccess(program);
        return this.volumeCalculatorUtils.calculateProgramVolume(program);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ProgramDto createProgram(final ProgramDto programDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        final DataBinder dataBinder = new DataBinder(programDto);
        dataBinder.addValidators(new ProgramDtoValidator());
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(ProgramDto.class, dataBinder.getBindingResult());
        }

        final Program program = this.modelMapper.map(programDto, Program.class);
        program.setUserApp(this.userDao.getReferenceById(programDto.getUserAppId()));
        this.authService.checkAccess(program);
        return this.modelMapper.map(this.programDao.save(program), ProgramDto.class);
    }

    /**
     * @ {@inheritDoc}
     */
    @Override
    @Transactional
    public ProgramDto updateProgram(final ProgramDto programDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        final DataBinder dataBinder = new DataBinder(programDto);
        dataBinder.addValidators(new ProgramDtoValidator());
        dataBinder.validate();
        if (dataBinder.getBindingResult().hasErrors()) {
            throw new BadFormException(ProgramDto.class, dataBinder.getBindingResult());
        }


        final Program programDb = this.programDao.getReferenceById(programDto.getId());
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
    public void deleteProgram(final Long programId)
            throws EntityNotFoundException, IllegalAccessException {
        final Program program = this.programDao.findById(programId)
                .orElseThrow(() -> new EntityNotFoundException(Program.class, programId));
        this.authService.checkAccess(program);
        this.programDao.deleteById(programId);
    }
}