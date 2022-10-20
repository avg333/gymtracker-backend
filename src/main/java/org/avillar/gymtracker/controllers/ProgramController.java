package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.services.ProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private final ProgramService programService;

    @Autowired
    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProgramDto>> getAllPrograms() {
        return ResponseEntity.ok(this.programService.getUserAllLoggedUserProgramsWithVolume());
    }

    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDto> getProgram(@PathVariable final Long programId) throws IllegalAccessException {
        return ResponseEntity.ok(this.programService.getProgram(programId));
    }

    @PostMapping("")
    public ResponseEntity<ProgramDto> postProgram(@Valid @RequestBody final ProgramDto programDto) {
        return ResponseEntity.ok(this.programService.createProgram(programDto));
    }

    @PutMapping("/{programId}")
    public ResponseEntity<ProgramDto> putProgram(@PathVariable final Long programId, @Valid @RequestBody final ProgramDto programDto) throws IllegalAccessException {
        if (!programId.equals(programDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.programService.updateProgram(programDto));
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable final Long programId) throws IllegalAccessException {
        this.programService.deleteProgram(programId);
        return ResponseEntity.ok().build();
    }

}
