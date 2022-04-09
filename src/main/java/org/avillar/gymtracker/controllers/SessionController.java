package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.dto.SessionDto;
import org.avillar.gymtracker.model.Session;
import org.avillar.gymtracker.services.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SessionController {

    private final SessionService sessionService;
    private final ModelMapper modelMapper;

    @Autowired
    public SessionController(ModelMapper modelMapper, SessionService sessionService) {
        this.modelMapper = modelMapper;
        this.sessionService = sessionService;
    }

    @GetMapping("program/{programId}/session")
    public ResponseEntity<List<SessionDto>> getAllProgramSessions(@PathVariable final Long programId) {
        final List<Session> sessions = this.sessionService.getAllProgramSessions(programId);
        final List<SessionDto> sessionsDto = sessions.stream().map(session -> modelMapper.map(session, SessionDto.class)).toList();
        return new ResponseEntity<>(sessionsDto, HttpStatus.OK);
    }

    @GetMapping("program/{programId}/session/{sessionId}")
    public ResponseEntity<SessionDto> getProgramSession(@PathVariable final Long programId, @PathVariable final Long sessionId) {
        final Session session = this.sessionService.getProgramSession(programId, sessionId);
        return new ResponseEntity<>(modelMapper.map(session, SessionDto.class), HttpStatus.OK);
    }

    @PostMapping("program/{programId}/session")
    public ResponseEntity<SessionDto> addSessionToProgram(@PathVariable final Long programId, @RequestBody final SessionDto sessionDto) {
        final Session sessionInput = modelMapper.map(sessionDto, Session.class);
        final Session session = this.sessionService.createSession(programId, sessionInput);
        return new ResponseEntity<>(modelMapper.map(session, SessionDto.class), HttpStatus.CREATED);
    }

    @PutMapping("program/{programId}/session/{sessionId}")
    public ResponseEntity<SessionDto> updateSession(@PathVariable final Long programId, @PathVariable final Long sessionId, @RequestBody final SessionDto sessionDto) {
        final Session sessionInput = modelMapper.map(sessionDto, Session.class);
        final Session session = this.sessionService.updateSession(programId, sessionId, sessionInput);
        return new ResponseEntity<>(modelMapper.map(session, SessionDto.class), HttpStatus.OK);
    }

    @DeleteMapping("program/{programId}/session/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable final Long programId, @PathVariable final Long sessionId) {
        this.sessionService.deleteSession(programId, sessionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
