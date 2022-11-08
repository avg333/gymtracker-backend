package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.services.ProgramService;
import org.avillar.gymtracker.utils.PaginatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramController {

    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/users/{userId}/programs/count")
    public long countUserPrograms(@PathVariable final Long userId) {
        return this.programService.getAllUserProgramsSize(userId);
    }

    @GetMapping("/users/{userId}/programs")
    public ResponseEntity<List<ProgramDto>> getAllProgramsFromUser(
            @PathVariable final Long userId,
            @RequestParam(required = false, defaultValue = "0") final Integer page,
            @RequestParam(required = false, defaultValue = "1") final Integer size,
            @RequestParam(required = false) final String sortParam,
            @RequestParam(required = false, defaultValue = "False") final Boolean descSort) {
        try {
            final Pageable pageable = PaginatorUtils.getPageable(page, size, sortParam, descSort);
            return ResponseEntity.ok(this.programService.getAllUserPrograms(userId, pageable));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/programs/{programId}")
    public ResponseEntity<ProgramDto> getProgram(@PathVariable final Long programId) {
        try {
            return ResponseEntity.ok(this.programService.getProgram(programId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/programs")
    public ResponseEntity<ProgramDto> postProgram(@RequestBody final ProgramDto programDto) {
        programDto.setId(null);

        return ResponseEntity.ok(this.programService.createProgram(programDto));
    }

    @PutMapping("/programs/{programId}")
    public ResponseEntity<ProgramDto> putProgram(@PathVariable final Long programId, @RequestBody final ProgramDto programDto) {
        programDto.setId(programId);

        try {
            return ResponseEntity.ok(this.programService.updateProgram(programDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
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
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}