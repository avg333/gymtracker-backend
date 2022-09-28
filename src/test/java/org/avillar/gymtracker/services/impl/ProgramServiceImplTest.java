package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.ProgramRepository;
import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
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
    private ProgramRepository programRepository;
    @Mock
    private LoginServiceImpl loginService;
    @InjectMocks
    private ModelMapper modelMapper;
    @InjectMocks
    private ProgramServiceImpl programService;

    @BeforeEach
    public void setUp() {
        final UserApp user = new UserApp();
        user.setId(USER_ID);
        when(loginService.getLoggedUser()).thenReturn(user);

        programService = new ProgramServiceImpl(programRepository, modelMapper, loginService);
    }

    @Test
    void getUserAllProgramsWithVolume() {
        final List<Program> programList = Arrays.asList(getProgramWithUserId(PROGRAM_ID, USER_ID, 2),
                getProgramWithUserId(PROGRAM_ID + 1, USER_ID, 3),
                getProgramWithUserId(PROGRAM_ID + 2, USER_ID, 0));

        when(this.programRepository.findByUserAppOrderByNameAsc(Mockito.any(UserApp.class))).thenReturn(programList);
        final List<ProgramDto> programListDtos = this.programService.getUserAllProgramsWithVolume();

        assertEquals(programList.size(), programListDtos.size());
        for (int i = 0; i < programList.size(); i++) {
            assertEquals(programList.get(i).getId(), programListDtos.get(i).getId());
            assertEquals(programList.get(i).getName(), programListDtos.get(i).getName());
            assertEquals(programList.get(i).getSessions().size(), programListDtos.get(i).getSessionNumber());
        }
    }

    @Test
    void getProgram() throws IllegalAccessException {
        Program program = getProgramWithUserId(PROGRAM_ID, USER_ID, 0);

        when(programRepository.findById(PROGRAM_ID)).thenReturn(Optional.of(program));
        final ProgramDto programDto = this.programService.getProgram(PROGRAM_ID);

        assertEquals(program.getId(), programDto.getId());
        assertEquals(program.getName(), programDto.getName());

        program = getProgramWithUserId(null, NOT_USER_ID, 0);
        when(programRepository.findById(PROGRAM_ID)).thenReturn(Optional.of(program));

        final Exception exception = assertThrows(IllegalAccessException.class, () -> this.programService.getProgram(PROGRAM_ID));
        assertEquals(NO_PERMISSIONS, exception.getMessage());
    }

    @Test
    void createProgram() {
        final ProgramDto programDtoInput = new ProgramDto();
        programDtoInput.setId(PROGRAM_ID);
        programDtoInput.setName(String.valueOf(PROGRAM_ID));
        programDtoInput.setLevel(ProgramLevelEnum.HARD);

        when(this.programRepository.save(Mockito.any(Program.class))).thenAnswer(i -> i.getArguments()[0]);
        final ProgramDto programDtoOutput = this.programService.createProgram(programDtoInput);

        assertEquals(programDtoInput.getId(), programDtoOutput.getId());
        assertEquals(programDtoInput.getName(), programDtoOutput.getName());
        assertEquals(programDtoInput.getLevel(), programDtoOutput.getLevel());
    }

    @Test
    void updateProgram() throws IllegalAccessException {
        final ProgramDto programDtoInput = new ProgramDto();
        programDtoInput.setId(PROGRAM_ID);
        programDtoInput.setName(String.valueOf(PROGRAM_ID));
        programDtoInput.setLevel(ProgramLevelEnum.HARD);
        when(this.programRepository.save(Mockito.any(Program.class))).thenAnswer(i -> i.getArguments()[0]);

        Program program = getProgramWithUserId(programDtoInput.getId(), USER_ID, 0);
        when(programRepository.findById(PROGRAM_ID)).thenReturn(Optional.of(program));
        final ProgramDto programDtoOutput = this.programService.updateProgram(programDtoInput);
        assertEquals(programDtoInput.getId(), programDtoOutput.getId());
        assertEquals(programDtoInput.getName(), programDtoOutput.getName());
        assertEquals(programDtoInput.getLevel(), programDtoOutput.getLevel());

        programDtoInput.setId(null);
        Exception exception = assertThrows(EntityNotFoundException.class, () -> this.programService.updateProgram(programDtoInput));
        assertEquals(NOT_FOUND_ERROR_MSG, exception.getMessage());

        program = getProgramWithUserId(programDtoInput.getId(), NOT_USER_ID, 0);
        when(programRepository.findById(PROGRAM_ID)).thenReturn(Optional.of(program));
        exception = assertThrows(IllegalAccessException.class, () -> this.programService.getProgram(PROGRAM_ID));
        assertEquals(NO_PERMISSIONS, exception.getMessage());
    }

    @Test
    void deleteProgram() {
        final Program program = getProgramWithUserId(PROGRAM_ID, NOT_USER_ID, 0);
        when(programRepository.findById(PROGRAM_ID)).thenReturn(Optional.of(program));
        Exception exception = assertThrows(IllegalAccessException.class, () -> this.programService.deleteProgram(PROGRAM_ID));
        assertEquals(NO_PERMISSIONS, exception.getMessage());
    }

    @Test
    void programExistsAndIsFromLoggedUser() {
        final Program program = getProgramWithUserId(PROGRAM_ID, NOT_USER_ID, 0);
        when(programRepository.findById(PROGRAM_ID)).thenReturn(Optional.of(program));
        Exception exception = assertThrows(IllegalAccessException.class, () -> this.programService.programExistsAndIsFromLoggedUser(PROGRAM_ID));
        assertEquals(NO_PERMISSIONS, exception.getMessage());

        when(programRepository.findById(NOT_PROGRAM_ID)).thenThrow(new EntityNotFoundException(NOT_FOUND_ERROR_MSG));
        exception = assertThrows(EntityNotFoundException.class, () -> this.programService.programExistsAndIsFromLoggedUser(NOT_PROGRAM_ID));
        assertEquals(NOT_FOUND_ERROR_MSG, exception.getMessage());
    }

    private Program getProgramWithUserId(final Long programId, final Long userId, final int numberSessions) {
        final UserApp userApp = new UserApp();
        userApp.setId(userId);
        final Program program = new Program();
        program.setId(programId);
        program.setName(String.valueOf(programId));
        program.setUserApp(userApp);

        final Set<Session> sessionsProgram = new HashSet<>();
        for (int i = 0; i < numberSessions; i++) {
            final Session session = new Session();
            session.setId((long) i);
            sessionsProgram.add(session);
        }
        program.setSessions(sessionsProgram);

        return program;
    }

}