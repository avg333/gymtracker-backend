package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.dto.ProgramDto;
import org.avillar.gymtracker.model.Program;
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
    public ProgramController(ModelMapper modelMapper, ProgramService programService) {
        this.modelMapper = modelMapper;
        this.programService = programService;
    }

    @GetMapping("")
    public ResponseEntity<List<ProgramDto>> getAllPrograms() {
        final List<Program> programs = this.programService.getAllPrograms();
        final TypeToken<List<ProgramDto>> typeToken = new TypeToken<>() {
        };
        final List<ProgramDto> programsDto = modelMapper.map(programs, typeToken.getType());
        return new ResponseEntity<>(programsDto, HttpStatus.OK);
    }

    @GetMapping("/{programId}")
    public ResponseEntity<ProgramDto> getProgram(@PathVariable Long programId) {
        final Program program = this.programService.getProgram(programId);
        return program != null ? new ResponseEntity<>(this.modelMapper.map(program, ProgramDto.class), HttpStatus.OK) :
                new ResponseEntity<>(new ProgramDto(), HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("")
    public ResponseEntity<ProgramDto> createProgram(@RequestBody ProgramDto programDto) {
        final Program programInput = this.modelMapper.map(programDto, Program.class);
        final Program program = this.programService.createProgram(programInput);
        return program != null ? new ResponseEntity<>(this.modelMapper.map(program, ProgramDto.class), HttpStatus.CREATED) :
                new ResponseEntity<>(new ProgramDto(), HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/{programId}")
    public ResponseEntity<ProgramDto> updateProgram(@PathVariable Long programId, @RequestBody ProgramDto programDto) {
        final Program programInput = this.modelMapper.map(programDto, Program.class);
        final Program program = this.programService.updateProgram(programId, programInput);
        return program != null ? new ResponseEntity<>(this.modelMapper.map(program, ProgramDto.class), HttpStatus.OK) :
                new ResponseEntity<>(new ProgramDto(), HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/{programId}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long programId) {
        this.programService.deleteProgram(programId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
