package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.dto.SetDto;
import org.avillar.gymtracker.model.Set;
import org.avillar.gymtracker.services.SetService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class SetController {

    private final SetService setService;
    private final ModelMapper modelMapper;

    public SetController(SetService setService, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.setService = setService;
    }

    @GetMapping("/{programId}/session/{sessionId}/set")
    public ResponseEntity<List<SetDto>> getAllSessionSets(@PathVariable final Long programId, @PathVariable final Long sessionId) {
        final List<Set> sets = this.setService.getAllSessionSets(programId, sessionId);
        final TypeToken<List<SetDto>> typeToken = new TypeToken<>() {
        };
        final List<SetDto> setsDto = modelMapper.map(sets, typeToken.getType());
        return new ResponseEntity<>(setsDto, HttpStatus.OK);
    }

    @GetMapping("/{programId}/session/{sessionId}/set/{setId}")
    public ResponseEntity<SetDto> getSessionSet(@PathVariable final Long programId, @PathVariable final Long sessionId, @PathVariable final Long setId) {
        final Set set = this.setService.getSessionSet(programId, sessionId, setId);
        return new ResponseEntity<>(modelMapper.map(set, SetDto.class), HttpStatus.OK);
    }

    @PostMapping("/{programId}/session/{sessionId}/set")
    public ResponseEntity<SetDto> addSetToSession(@PathVariable final Long programId, @PathVariable final Long sessionId, @RequestBody SetDto setDto) {
        final Set setInput = this.modelMapper.map(setDto, Set.class);
        final Set set = this.setService.createSet(programId, sessionId, setInput);
        return new ResponseEntity<>(modelMapper.map(set, SetDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{programId}/session/{sessionId}/set/{setId}")
    public ResponseEntity<SetDto> updateSet(@PathVariable final Long programId, @PathVariable final Long sessionId, @PathVariable final Long setId, @RequestBody SetDto setDto) {
        final Set setInput = this.modelMapper.map(setDto, Set.class);
        final Set set = this.setService.updateSet(programId, sessionId, setId, setInput);
        return new ResponseEntity<>(modelMapper.map(set, SetDto.class), HttpStatus.OK);
    }

    @DeleteMapping("/{programId}/session/{sessionId}/set/{setId}")
    public ResponseEntity<Void> deleteSet(@PathVariable final Long programId, @PathVariable final Long sessionId, @PathVariable final Long setId) {
        this.setService.deleteSet(programId, sessionId, setId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
