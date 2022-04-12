package org.avillar.gymtracker.api.controllers;

import org.avillar.gymtracker.api.dto.ProgramDto;
import org.avillar.gymtracker.exceptions.ResourceNotExistsException;
import org.avillar.gymtracker.model.entities.Program;
import org.avillar.gymtracker.services.ProgramService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {

    private final ProgramService programService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProgramController(ProgramService programService, ModelMapper modelMapper) {
        this.programService = programService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<ProgramDto>> getAllPrograms() {
        final List<Program> programs = this.programService.getAllPrograms();
        final TypeToken<List<ProgramDto>> typeToken = new TypeToken<>() {
        };
        return ResponseEntity.ok(this.modelMapper.map(programs, typeToken.getType()));
    }

    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDto> getProgram(@PathVariable final Long programId) throws ResourceNotExistsException {
        final Program program = this.programService.getProgram(programId);
        return ResponseEntity.ok(this.modelMapper.map(program, ProgramDto.class));
    }

    @PostMapping("")
    public ResponseEntity<ProgramDto> createProgram(@RequestBody final ProgramDto programDto) {
        final Program programInput = this.modelMapper.map(programDto, Program.class);
        final Program program = this.programService.createProgram(programInput);
        return ResponseEntity.ok(this.modelMapper.map(program, ProgramDto.class));
    }

    @PutMapping("/{programId}")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable final Long programId, @RequestBody final ProgramDto programDto) throws ResourceNotExistsException {
        final Program programInput = this.modelMapper.map(programDto, Program.class);
        final Program program = this.programService.updateProgram(programId, programInput);
        return ResponseEntity.ok(this.modelMapper.map(program, ProgramDto.class));
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable final Long programId) throws ResourceNotExistsException {
        this.programService.deleteProgram(programId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
