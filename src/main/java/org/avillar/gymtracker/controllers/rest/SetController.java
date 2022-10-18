package org.avillar.gymtracker.controllers.rest;

import org.avillar.gymtracker.model.dto.SetDto;
import org.avillar.gymtracker.services.SetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<SetDto>> getAllSetGroupSets(@PathVariable final Long setGroupId) throws IllegalAccessException {
        return ResponseEntity.ok(this.setService.getAllSetGroupSets(setGroupId));
    }

    @GetMapping("sets/{setId}")
    public ResponseEntity<SetDto> getSet(@PathVariable final Long setId) throws IllegalAccessException {
        return ResponseEntity.ok(this.setService.getSet(setId));
    }

    @PostMapping("sets")
    public ResponseEntity<SetDto> postSet(@RequestBody final SetDto setDto) throws IllegalAccessException {
        return ResponseEntity.ok(this.setService.createSet(setDto));
    }

    @PutMapping("sets/{setId}")
    public ResponseEntity<SetDto> updateSet(@PathVariable final Long setId, @RequestBody final SetDto setDto) throws IllegalAccessException {
        if (!setId.equals(setDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.setService.updateSet(setDto));
    }

    @DeleteMapping("sets/{setId}")
    public ResponseEntity<Void> deleteSet(@PathVariable final Long setId) throws IllegalAccessException {
        this.setService.deleteSet(setId);
        return ResponseEntity.ok().build();
    }
}
