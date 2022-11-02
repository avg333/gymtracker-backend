package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.services.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProgramController {

    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("/users/{userId}/programs")
    public ResponseEntity<List<ProgramDto>> getAllProgramsFromUser(@PathVariable final Long userId) throws IllegalAccessException {
        return ResponseEntity.ok(this.programService.getAllUserPrograms(userId));
    }

    @GetMapping("/programs/{programId}")
    public ResponseEntity<ProgramDto> getProgram(@PathVariable final Long programId) throws IllegalAccessException {
        return ResponseEntity.ok(this.programService.getProgram(programId));
    }

    @PostMapping("/programs")
    public ResponseEntity<ProgramDto> postProgram(@RequestBody final ProgramDto programDto) {
        if (null != programDto.getId())
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(this.programService.createProgram(programDto));
    }

    @PutMapping("/programs/{programId}")
    public ResponseEntity<ProgramDto> putProgram(@PathVariable final Long programId, @RequestBody final ProgramDto programDto) throws IllegalAccessException {
        if (!programId.equals(programDto.getId()))
            return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(this.programService.updateProgram(programDto));
    }

    @DeleteMapping("/programs/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable final Long programId) throws IllegalAccessException {
        this.programService.deleteProgram(programId);
        return ResponseEntity.ok().build();
    }
}