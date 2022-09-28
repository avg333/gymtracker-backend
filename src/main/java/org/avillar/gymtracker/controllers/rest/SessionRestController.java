package org.avillar.gymtracker.controllers.rest;

import org.avillar.gymtracker.model.dto.SessionDto;
import org.avillar.gymtracker.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SessionRestController {

    private final SessionService sessionService;

    @Autowired
    public SessionRestController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @GetMapping("/programs/{programId}/sessions")
    public ResponseEntity<List<SessionDto>> getAllProgramSessions(@PathVariable final Long programId) throws IllegalAccessException {
        return ResponseEntity.ok(this.sessionService.getAllProgramSessionsWithData(programId));
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<SessionDto> getSession(@PathVariable final Long sessionId) throws IllegalAccessException {
        return ResponseEntity.ok(this.sessionService.getSession(sessionId));
    }

    @PostMapping("/sessions")
    public ResponseEntity<SessionDto> postSession(@RequestBody final SessionDto sessionDto) throws IllegalAccessException {
        return ResponseEntity.ok(this.sessionService.createSessionInProgram(sessionDto));
    }

    @PutMapping("/sessions/{sessionId}")
    public ResponseEntity<SessionDto> putSession(@PathVariable final Long sessionId, @RequestBody final SessionDto sessionDto) throws IllegalAccessException {
        if (!sessionId.equals(sessionDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.sessionService.updateSession(sessionDto));
    }

    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable final Long sessionId) throws IllegalAccessException {
        this.sessionService.deleteSession(sessionId);
        return ResponseEntity.ok().build();
    }

}
