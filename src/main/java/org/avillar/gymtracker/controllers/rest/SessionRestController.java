package org.avillar.gymtracker.controllers.rest;

import org.avillar.gymtracker.model.dto.SessionDto;
import org.avillar.gymtracker.model.entities.Session;
import org.avillar.gymtracker.services.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class SessionRestController {

    private final SessionService sessionService;
    private final ModelMapper modelMapper;

    @Autowired
    public SessionRestController(SessionService sessionService, ModelMapper modelMapper) {
        this.sessionService = sessionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{programId}/sessions")
    public ResponseEntity<List<SessionDto>> getAllProgramSessions(@PathVariable final Long programId) {
        final List<Session> sessions = this.sessionService.getAllProgramSessions(programId);
        final List<SessionDto> sessionDtos = new ArrayList<>();
        for (final Session session : sessions) {
            final SessionDto sessionDto = this.modelMapper.map(session, SessionDto.class);
            sessionDto.setSetsNumber(session.getSets().size());
            sessionDto.setExercisesNumber(session.getSets().size());
            sessionDtos.add(sessionDto);
        }
        return ResponseEntity.ok(sessionDtos);
    }

    @GetMapping("/{programId}/sessions/{sessionId}")
    public ResponseEntity<SessionDto> getProgramSession(@PathVariable final Long programId, @PathVariable final Long sessionId) {
        final Session session = this.sessionService.getProgramSession(programId, sessionId);
        return ResponseEntity.ok(this.modelMapper.map(session, SessionDto.class));
    }

    @PostMapping("/{programId}/sessions")
    public ResponseEntity<SessionDto> addSessionToProgram(@PathVariable final Long programId, @RequestBody final SessionDto sessionDto) {
        final Session sessionInput = this.modelMapper.map(sessionDto, Session.class);
        final Session session = this.sessionService.createSession(programId, sessionInput);
        return ResponseEntity.ok(this.modelMapper.map(session, SessionDto.class));
    }

    @PutMapping("/{programId}/sessions/{sessionId}")
    public ResponseEntity<SessionDto> updateSession(@PathVariable final Long programId, @PathVariable final Long sessionId, @RequestBody final SessionDto sessionDto) {
        final Session sessionInput = this.modelMapper.map(sessionDto, Session.class);
        final Session session = this.sessionService.updateSession(programId, sessionId, sessionInput);
        return ResponseEntity.ok(this.modelMapper.map(session, SessionDto.class));
    }

    @DeleteMapping("/{programId}/sessions/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable final Long programId, @PathVariable final Long sessionId) {
        this.sessionService.deleteSession(programId, sessionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
