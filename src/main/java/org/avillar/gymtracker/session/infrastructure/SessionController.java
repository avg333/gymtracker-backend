package org.avillar.gymtracker.session.infrastructure;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.infrastructure.BaseController;
import org.avillar.gymtracker.program.application.ProgramDto;
import org.avillar.gymtracker.session.application.SessionService;
import org.avillar.gymtracker.session.application.dto.SessionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SessionController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionController.class);

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
            LOGGER.info("Unauthorized access program={} sessions by user={}",
                    programId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing program={} sessions by user={}",
                    programId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sessions/{sessionId}")
    public ResponseEntity<SessionDto> getSession(@PathVariable final Long sessionId) {
        try {
            return ResponseEntity.ok(this.sessionService.getSession(sessionId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access session={} by user={}",
                    sessionId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing session={} by user={}",
                    sessionId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/programs/{programId}/sessions")
    public ResponseEntity<SessionDto> postSession(@PathVariable final Long programId, @RequestBody final SessionDto sessionDto) {
        sessionDto.setId(null);
        final ProgramDto programDto = new ProgramDto();
        programDto.setId(programId);
        sessionDto.setProgramDto(programDto);

        try {
            return ResponseEntity.ok(this.sessionService.createSession(sessionDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access creating session for program={} by user={}",
                    programId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error creating session for program={} by user={}",
                    programId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
            LOGGER.info("Unauthorized access to update session={} by user={}",
                    sessionId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error updating session={} by user={}",
                    sessionId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/sessions/{sessionId}")
    public ResponseEntity<Void> deleteSession(@PathVariable final Long sessionId) {
        try {
            this.sessionService.deleteSession(sessionId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to remove session={} by user={}",
                    sessionId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error removing session={} by user={}",
                    sessionId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}