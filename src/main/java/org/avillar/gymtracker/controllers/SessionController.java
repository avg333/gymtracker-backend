package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.SessionDto;
import org.avillar.gymtracker.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
    public ResponseEntity<List<SessionDto>> getAllProgramSessions(@PathVariable final Long programId) throws IllegalAccessException {
        return ResponseEntity.ok(this.sessionService.getAllProgramSessionsWithData(programId));
    }

    @GetMapping("/sessions")
    public ResponseEntity<List<SessionDto>> getAllNotProgramsLoggedUserSessionsWithData() {
        return ResponseEntity.ok(this.sessionService.getAllNotProgramsLoggedUserSessionsWithData());
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<SessionDto> getSession(@PathVariable final Long sessionId) throws IllegalAccessException {
        return ResponseEntity.ok(this.sessionService.getSession(sessionId));
    }

    @GetMapping("/sessions/bydate")
    public ResponseEntity<List<SessionDto>> getSessionsByDate(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") final Date date) {

        return ResponseEntity.ok(this.sessionService.getSessionByDate(date));
    }

    @PostMapping("/sessions")
    public ResponseEntity<SessionDto> postSession(@RequestBody final SessionDto sessionDto) throws IllegalAccessException {
        return ResponseEntity.ok(sessionDto.getProgramId() != null
                ? this.sessionService.createSessionInProgram(sessionDto)
                : this.sessionService.createSession(sessionDto));
    }

    @PutMapping("/sessions/{sessionId}")
    public ResponseEntity<SessionDto> putSession(@PathVariable final Long sessionId, @RequestBody final SessionDto sessionDto) throws IllegalAccessException {
        if (!sessionId.equals(sessionDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(sessionDto.getProgramId() != null
                ? this.sessionService.updateProgramSession(sessionDto)
                : this.sessionService.updateSession(sessionDto));
    }

    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable final Long sessionId) throws IllegalAccessException {
        this.sessionService.deleteSession(sessionId);
        return ResponseEntity.ok().build();
    }

}
