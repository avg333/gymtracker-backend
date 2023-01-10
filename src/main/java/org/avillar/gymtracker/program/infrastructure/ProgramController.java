package org.avillar.gymtracker.program.infrastructure;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.base.infrastructure.BaseController;
import org.avillar.gymtracker.program.application.ProgramDto;
import org.avillar.gymtracker.program.application.ProgramService;
import org.avillar.gymtracker.utils.infraestructure.PaginatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProgramController.class);

    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/users/{userId}/programs/count")
    public ResponseEntity<Long> getNumberOfProgramsFromUser(@PathVariable final Long userId) {
        try {
            return ResponseEntity.ok(this.programService.getAllUserProgramsSize(userId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access user={} programs by user={}",
                    userId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing user={} programs by user={}",
                    userId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userId}/programs")
    public ResponseEntity<List<ProgramDto>> getAllUserPrograms(
            @PathVariable final Long userId,
            @RequestParam(required = false, defaultValue = "0") final Integer page,
            @RequestParam(required = false, defaultValue = "1") final Integer size,
            @RequestParam(required = false) final String sortParam,
            @RequestParam(required = false, defaultValue = "False") final Boolean descSort) {
        final Pageable pageable = PaginatorUtils.getPageable(page, size, sortParam, descSort);
        try {
            return ResponseEntity.ok(this.programService.getAllUserPrograms(userId, pageable));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access user={} programs by user={}",
                    userId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing user={} programs by user={}",
                    userId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/programs/{programId}")
    public ResponseEntity<ProgramDto> getProgram(@PathVariable final Long programId) {
        try {
            return ResponseEntity.ok(this.programService.getProgram(programId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access program={} by user={}",
                    programId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error accessing program={} by user={}",
                    programId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users/{userId}/programs")
    public ResponseEntity<ProgramDto> postProgram(@PathVariable final Long userId, @RequestBody final ProgramDto programDto) {
        programDto.setId(null);
        programDto.setUserAppId(userId);

        try {
            return ResponseEntity.ok(this.programService.createProgram(programDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access creating program for user={} by user={}",
                    userId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error creating program for user={} by user={}",
                    userId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/programs/{programId}")
    public ResponseEntity<ProgramDto> putProgram(@PathVariable final Long programId, @RequestBody final ProgramDto programDto) {
        programDto.setId(programId);

        try {
            return ResponseEntity.ok(this.programService.updateProgram(programDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to update program={} by user={}",
                    programId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error updating program={} by user={}",
                    programId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/programs/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable final Long programId) {
        try {
            this.programService.deleteProgram(programId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            LOGGER.info("Unauthorized access to remove program={} by user={}",
                    programId, this.authService.getLoggedUser().getId());
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (Exception exception) {
            LOGGER.error("Error removing program={} by user={}",
                    programId, this.authService.getLoggedUser().getId(), exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}