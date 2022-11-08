package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.SetGroupDto;
import org.avillar.gymtracker.services.SetGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SetGroupController {

    private final SetGroupService setGroupService;

    @Autowired
    public SetGroupController(SetGroupService setGroupService) {
        this.setGroupService = setGroupService;
    }

    @GetMapping("/sessions/{sessionId}/setGroups")
    public ResponseEntity<List<SetGroupDto>> getAllSessionSetGroups(@PathVariable final Long sessionId) {
        try {
            return ResponseEntity.ok(this.setGroupService.getAllSessionSetGroups(sessionId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/workouts/{workoutId}/setGroups")
    public ResponseEntity<List<SetGroupDto>> getAllWorkoutSetGroups(@PathVariable final Long workoutId) {
        try {
            return ResponseEntity.ok(this.setGroupService.getAllWorkoutSetGroups(workoutId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/setGroups/{setGroupId}")
    public ResponseEntity<SetGroupDto> getSetGroup(@PathVariable final Long setGroupId) {
        try {
            return ResponseEntity.ok(this.setGroupService.getSetGroup(setGroupId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/setGroups")
    public ResponseEntity<SetGroupDto> postSetGroup(@RequestBody final SetGroupDto setGroupDto) {
        setGroupDto.setId(null);

        try {
            return ResponseEntity.ok(this.setGroupService.createSetGroup(setGroupDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("/setGroups/{setGroupId}")
    public ResponseEntity<SetGroupDto> putSetGroup(@PathVariable final Long setGroupId, @RequestBody final SetGroupDto setGroupDto) {
        setGroupDto.setId(setGroupId);

        try {
            return ResponseEntity.ok(this.setGroupService.updateSetGroup(setGroupDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("/setGroups/{setGroupId}")
    public ResponseEntity<Void> deleteSetGroup(@PathVariable final Long setGroupId) {
        try {
            this.setGroupService.deleteSetGroup(setGroupId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}