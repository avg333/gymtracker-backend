package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.ProgramRepository;
import org.avillar.gymtracker.model.dto.ProgramListDto;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.model.entities.User;
import org.avillar.gymtracker.model.enums.ProgramLevelEnum;
import org.avillar.gymtracker.services.ProgramService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class ProgramServiceImplTest {

    @Mock
    private ProgramRepository programRepository;
    @InjectMocks
    private ModelMapper modelMapper;

    private ProgramServiceImpl programService;

    @BeforeEach
    void setUp() {
        programService = new ProgramServiceImpl(programRepository, modelMapper);
    }

    @Test
    void getUserAllPrograms() {
        User user = new User();
        Program program1 = new Program();
        program1.setName("NameProgram1");
        program1.setLevel(ProgramLevelEnum.HARD);
        Program program2 = new Program();
        program2.setName("NameProgram2");
        program2.setLevel(ProgramLevelEnum.MEDIUM);
        List<Program> programList = new ArrayList<>();
        programList.add(program1);
        programList.add(program2);

        Set<Session> sessionsProgram1 = new HashSet<>();
        Session session11 = new Session();
        session11.setId(11L);
        Session session12 = new Session();
        session11.setId(12L);
        sessionsProgram1.add(session11);
        sessionsProgram1.add(session12);
        program1.setSessions(sessionsProgram1);
        Set<Session> sessionsProgram2 = new HashSet<>();
        Session session21 = new Session();
        session11.setId(21L);
        Session session22 = new Session();
        session11.setId(22L);
        Session session23 = new Session();
        session11.setId(23L);
        sessionsProgram2.add(session21);
        sessionsProgram2.add(session22);
        sessionsProgram2.add(session23);
        program2.setSessions(sessionsProgram2);

        when(programRepository.findByUserOrderByNameAsc(user)).thenReturn(programList);

        List<ProgramListDto> programListDtos = programService.getUserAllPrograms(user);
        assertEquals(2, programListDtos.size());
        assertEquals("NameProgram1", programListDtos.get(0).getName());
        assertEquals(ProgramLevelEnum.HARD, programListDtos.get(0).getLevel());
        assertEquals(2, programListDtos.get(0).getSessionNumber());
        assertEquals("NameProgram2", programListDtos.get(1).getName());
        assertEquals(ProgramLevelEnum.MEDIUM, programListDtos.get(1).getLevel());
        assertEquals(3, programListDtos.get(1).getSessionNumber());

    }
}