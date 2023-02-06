package org.avillar.gymtracker.session.infrastructure;

import org.avillar.gymtracker.errors.application.BadFormException;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.IllegalAccessException;
import org.avillar.gymtracker.program.application.dto.ProgramDto;
import org.avillar.gymtracker.session.application.SessionService;
import org.avillar.gymtracker.session.application.dto.SessionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<SessionDto>> getAllProgramSessions(@PathVariable final Long programId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.sessionService.getAllProgramSessions(programId));
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<SessionDto> getSession(@PathVariable final Long sessionId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.sessionService.getSession(sessionId));
    }

    @PostMapping("/programs/{programId}/sessions")
    public ResponseEntity<SessionDto> postSession(@PathVariable final Long programId, @RequestBody final SessionDto sessionDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        sessionDto.setId(null);
        sessionDto.setProgramDto(new ProgramDto(programId));

        return ResponseEntity.ok(this.sessionService.createSession(sessionDto));
        //TODO Validate
    }

    @PutMapping("/sessions/{sessionId}")
    public ResponseEntity<SessionDto> putSession(@PathVariable final Long sessionId, @RequestBody final SessionDto sessionDto)
            throws EntityNotFoundException, IllegalAccessException, BadFormException {
        sessionDto.setId(sessionId);

        return ResponseEntity.ok(this.sessionService.updateSession(sessionDto));
    }

    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable final Long sessionId)
            throws EntityNotFoundException, IllegalAccessException {
        this.sessionService.deleteSession(sessionId);
        return ResponseEntity.ok().build();
    }

}
