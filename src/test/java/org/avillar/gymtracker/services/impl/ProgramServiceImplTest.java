package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.ProgramDao;
import org.avillar.gymtracker.model.dao.UserDao;
import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ProgramServiceImplTest {

    private static final long PROGRAM_ID = 0L;
    private static final long NOT_PROGRAM_ID = 1L;
    private static final long USER_ID = 2L;
    private static final long NOT_USER_ID = 3L;
    private static final String NOT_FOUND_ERROR_MSG = "El programa no existe";
    private static final String NO_PERMISSIONS = "El usuario logeado no tiene permisos para acceder al recurso";

    @Mock
    private ProgramDao programDao;
    @Mock
    private LoginServiceImpl loginService;
    @InjectMocks
    private ModelMapper modelMapper;
    @InjectMocks
    private ProgramServiceImpl programService;
    @InjectMocks
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        UserApp user = new UserApp();
        user.setId(ProgramServiceImplTest.USER_ID);
        when(this.loginService.getLoggedUser()).thenReturn(user);

        this.programService = new ProgramServiceImpl(this.programDao, this.modelMapper, this.loginService, this.userDao);
    }

    @Test
    void getUserAllProgramsWithVolume() {
        List<Program> programList = Arrays.asList(this.getProgramWithUserId(ProgramServiceImplTest.PROGRAM_ID, ProgramServiceImplTest.USER_ID, 2),
                this.getProgramWithUserId(ProgramServiceImplTest.PROGRAM_ID + 1, ProgramServiceImplTest.USER_ID, 3),
                this.getProgramWithUserId(ProgramServiceImplTest.PROGRAM_ID + 2, ProgramServiceImplTest.USER_ID, 0));

        when(programDao.findByUserAppOrderByNameAsc(ArgumentMatchers.any(UserApp.class))).thenReturn(programList);
        List<ProgramDto> programListDtos = programService.getAllUserPrograms(ProgramServiceImplTest.USER_ID);

        assertEquals(programList.size(), programListDtos.size());
        for (int i = 0; i < programList.size(); i++) {
            assertEquals(programList.get(i).getId(), programListDtos.get(i).getId());
            assertEquals(programList.get(i).getName(), programListDtos.get(i).getName());
            assertEquals(programList.get(i).getSessions().size(), programListDtos.get(i).getSessionNumber());
        }
    }

    @Test
    void getProgram() throws IllegalAccessException {
        Program program = this.getProgramWithUserId(ProgramServiceImplTest.PROGRAM_ID, ProgramServiceImplTest.USER_ID, 0);

        when(this.programDao.findById(ProgramServiceImplTest.PROGRAM_ID)).thenReturn(Optional.of(program));
        ProgramDto programDto = programService.getProgram(ProgramServiceImplTest.PROGRAM_ID);

        assertEquals(program.getId(), programDto.getId());
        assertEquals(program.getName(), programDto.getName());

        program = this.getProgramWithUserId(null, ProgramServiceImplTest.NOT_USER_ID, 0);
        when(this.programDao.findById(ProgramServiceImplTest.PROGRAM_ID)).thenReturn(Optional.of(program));

        Exception exception = assertThrows(IllegalAccessException.class, () -> programService.getProgram(ProgramServiceImplTest.PROGRAM_ID));
        assertEquals(ProgramServiceImplTest.NO_PERMISSIONS, exception.getMessage());
    }

    @Test
    void createProgram() {
        ProgramDto programDtoInput = new ProgramDto();
        programDtoInput.setId(ProgramServiceImplTest.PROGRAM_ID);
        programDtoInput.setName(String.valueOf(ProgramServiceImplTest.PROGRAM_ID));
        programDtoInput.setLevel(ProgramLevelEnum.HARD);

        when(programDao.save(ArgumentMatchers.any(Program.class))).thenAnswer(i -> i.getArguments()[0]);
        ProgramDto programDtoOutput = programService.createProgram(programDtoInput);

        assertEquals(programDtoInput.getId(), programDtoOutput.getId());
        assertEquals(programDtoInput.getName(), programDtoOutput.getName());
        assertEquals(programDtoInput.getLevel(), programDtoOutput.getLevel());
    }

    @Test
    void updateProgram() throws IllegalAccessException {
        ProgramDto programDtoInput = new ProgramDto();
        programDtoInput.setId(ProgramServiceImplTest.PROGRAM_ID);
        programDtoInput.setName(String.valueOf(ProgramServiceImplTest.PROGRAM_ID));
        programDtoInput.setLevel(ProgramLevelEnum.HARD);
        when(programDao.save(ArgumentMatchers.any(Program.class))).thenAnswer(i -> i.getArguments()[0]);

        Program program = this.getProgramWithUserId(programDtoInput.getId(), ProgramServiceImplTest.USER_ID, 0);
        when(this.programDao.findById(ProgramServiceImplTest.PROGRAM_ID)).thenReturn(Optional.of(program));
        ProgramDto programDtoOutput = programService.updateProgram(programDtoInput);
        assertEquals(programDtoInput.getId(), programDtoOutput.getId());
        assertEquals(programDtoInput.getName(), programDtoOutput.getName());
        assertEquals(programDtoInput.getLevel(), programDtoOutput.getLevel());

        programDtoInput.setId(null);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> programService.updateProgram(programDtoInput));
        assertEquals(ProgramServiceImplTest.NOT_FOUND_ERROR_MSG, exception.getMessage());

        program = this.getProgramWithUserId(programDtoInput.getId(), ProgramServiceImplTest.NOT_USER_ID, 0);
        when(this.programDao.findById(ProgramServiceImplTest.PROGRAM_ID)).thenReturn(Optional.of(program));
        exception = assertThrows(IllegalAccessException.class, () -> programService.getProgram(ProgramServiceImplTest.PROGRAM_ID));
        assertEquals(ProgramServiceImplTest.NO_PERMISSIONS, exception.getMessage());
    }

    @Test
    void deleteProgram() {
        Program program = this.getProgramWithUserId(ProgramServiceImplTest.PROGRAM_ID, ProgramServiceImplTest.NOT_USER_ID, 0);
        when(this.programDao.findById(ProgramServiceImplTest.PROGRAM_ID)).thenReturn(Optional.of(program));
        final Exception exception = assertThrows(IllegalAccessException.class, () -> programService.deleteProgram(ProgramServiceImplTest.PROGRAM_ID));
        assertEquals(ProgramServiceImplTest.NO_PERMISSIONS, exception.getMessage());
    }

    @Test
    void programExistsAndIsFromLoggedUser() {
        Program program = this.getProgramWithUserId(ProgramServiceImplTest.PROGRAM_ID, ProgramServiceImplTest.NOT_USER_ID, 0);
        when(this.programDao.findById(ProgramServiceImplTest.PROGRAM_ID)).thenReturn(Optional.of(program));
        Exception exception = assertThrows(IllegalAccessException.class, () -> programService.programExistsAndIsFromLoggedUser(ProgramServiceImplTest.PROGRAM_ID));
        assertEquals(ProgramServiceImplTest.NO_PERMISSIONS, exception.getMessage());

        when(this.programDao.findById(ProgramServiceImplTest.NOT_PROGRAM_ID)).thenThrow(new EntityNotFoundException(ProgramServiceImplTest.NOT_FOUND_ERROR_MSG));
        exception = assertThrows(EntityNotFoundException.class, () -> programService.programExistsAndIsFromLoggedUser(ProgramServiceImplTest.NOT_PROGRAM_ID));
        assertEquals(ProgramServiceImplTest.NOT_FOUND_ERROR_MSG, exception.getMessage());
    }

    private Program getProgramWithUserId(Long programId, Long userId, int numberSessions) {
        UserApp userApp = new UserApp();
        userApp.setId(userId);
        Program program = new Program();
        program.setId(programId);
        program.setName(String.valueOf(programId));
        program.setUserApp(userApp);

        Set<Session> sessionsProgram = new HashSet<>();
        for (int i = 0; i < numberSessions; i++) {
            Session session = new Session();
            session.setId((long) i);
            sessionsProgram.add(session);
        }
        program.setSessions(sessionsProgram);

        return program;
    }

}