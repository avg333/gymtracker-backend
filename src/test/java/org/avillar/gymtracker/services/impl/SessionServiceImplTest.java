package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.SessionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class SessionServiceImplTest {

    private static final long PROGRAM_ID = 0L;
    private static final long NOT_PROGRAM_ID = 1L;
    private static final long USER_ID = 2L;
    private static final long NOT_USER_ID = 3L;
    private static final String NOT_FOUND_ERROR_MSG = "El programa no existe";
    private static final String NO_PERMISSIONS = "El usuario logeado no tiene permisos para acceder al recurso";

    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private LoginServiceImpl loginService;
    @Mock
    private ProgramServiceImpl programService;
    @InjectMocks
    private ModelMapper modelMapper;

    @InjectMocks
    private SessionServiceImpl sessionService;

    @BeforeEach
    void setUp() {
//        final UserApp user = new UserApp();
//        user.setId(USER_ID);
//        when(loginService.getLoggedUser()).thenReturn(user);
//
//        sessionService = new SessionServiceImpl(sessionRepository, programService, modelMapper);
    }

    @Test
    void getAllProgramSessions() {
    }

    @Test
    void getSession() {
    }

    @Test
    void createSessionInProgram() {
    }

    @Test
    void updateSession() {
    }

    @Test
    void deleteSession() {
    }
}