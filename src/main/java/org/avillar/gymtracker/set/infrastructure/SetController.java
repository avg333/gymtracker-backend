package org.avillar.gymtracker.set.infrastructure;

import org.avillar.gymtracker.set.application.SetDto;
import org.avillar.gymtracker.set.application.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class SetController {

    private final SetService setService;

    @Autowired
    public SetController(SetService setService) {
        this.setService = setService;
    }

    @GetMapping("setGroups/{setGroupId}/sets")
    public ResponseEntity<List<SetDto>> getAllSetGroupSets(@PathVariable final Long setGroupId) {
        try {
            return ResponseEntity.ok(this.setService.getAllSetGroupSets(setGroupId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("sets/{setId}")
    public ResponseEntity<SetDto> getSet(@PathVariable final Long setId) {
        try {
            return ResponseEntity.ok(this.setService.getSet(setId));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("sets")
    public ResponseEntity<SetDto> postSet(@RequestBody final SetDto setDto) {
        setDto.setId(null);

        try {
            return ResponseEntity.ok(this.setService.createSet(setDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PutMapping("sets/{setId}")
    public ResponseEntity<SetDto> updateSet(@PathVariable final Long setId, @RequestBody final SetDto setDto) {
        setDto.setId(setId);

        try {
            return ResponseEntity.ok(this.setService.updateSet(setDto));
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @DeleteMapping("sets/{setId}")
    public ResponseEntity<Void> deleteSet(@PathVariable final Long setId) {
        try {
            this.setService.deleteSet(setId);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalAccessException exception) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }
}