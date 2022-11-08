package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.SessionDto;
import org.avillar.gymtracker.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/programs/{programId}/sessions")
    public ResponseEntity<List<SessionDto>> getAllProgramSessions(@PathVariable final Long programId) {
        try {
            return ResponseEntity.ok(this.sessionService.getAllProgramSessions(programId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<SessionDto> getSession(@PathVariable final Long sessionId) {
        try {
            return ResponseEntity.ok(this.sessionService.getSession(sessionId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/sessions")
    public ResponseEntity<SessionDto> postSession(@RequestBody final SessionDto sessionDto) {
        sessionDto.setId(null);

        try {
            return ResponseEntity.ok(this.sessionService.createSession(sessionDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/sessions/{sessionId}")
    public ResponseEntity<SessionDto> putSession(@PathVariable final Long sessionId, @RequestBody final SessionDto sessionDto) {
        sessionDto.setId(sessionId);

        try {
            return ResponseEntity.ok(this.sessionService.updateSession(sessionDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable final Long sessionId) {
        try {
            this.sessionService.deleteProgram(sessionId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}