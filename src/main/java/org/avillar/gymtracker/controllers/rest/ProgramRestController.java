package org.avillar.gymtracker.controllers.rest;

import org.avillar.gymtracker.model.dto.ProgramDto;
import org.avillar.gymtracker.services.ProgramService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramRestController {

    private final ProgramService programService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProgramRestController(ProgramService programService, ModelMapper modelMapper) {
        this.programService = programService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<ProgramDto>> getAllPrograms() {
        final List<ProgramDto> programs = this.programService.getUserAllProgramsWithVolume();
        return ResponseEntity.ok(programs);
    }

    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDto> getProgram(@PathVariable final Long programId) throws IllegalAccessException {
        return ResponseEntity.ok(this.programService.getProgram(programId));
    }

    @PostMapping("")
    public ResponseEntity<ProgramDto> createProgram(@RequestBody final ProgramDto programDto) {
        return ResponseEntity.ok(this.programService.createProgram(programDto));
    }

    @PutMapping("/{programId}")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable final Long programId, @RequestBody final ProgramDto programDto) throws IllegalAccessException {
        programDto.setId(programId);
        return ResponseEntity.ok(this.programService.updateProgram(programDto));
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable final Long programId) throws IllegalAccessException {
        this.programService.deleteProgram(programId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
