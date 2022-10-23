package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.SetGroupDto;
import org.avillar.gymtracker.services.SetGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SetGroupController {
    private static final String NOT_FOUND_ERROR_MSG = "El SetGroup no existe";

    private final SetGroupService setGroupService;

    @Autowired
    public SetGroupController(SetGroupService setGroupService) {
        this.setGroupService = setGroupService;
    }

    @GetMapping("/session/{sessionId}/setGroups")
    public ResponseEntity<List<SetGroupDto>> getAllSessionSetGroups(@PathVariable final Long sessionId) throws IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.getAllSessionSetGroups(sessionId));
    }

    @GetMapping("/setGroups/{setGroupId}")
    public ResponseEntity<SetGroupDto> getSetGroup(@PathVariable final Long setGroupId) throws IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.getSetGroup(setGroupId));
    }

    @PostMapping("/setGroups")
    public ResponseEntity<SetGroupDto> postSetGroup(@RequestBody final SetGroupDto setGroupDto) throws IllegalAccessException {
        return ResponseEntity.ok(this.setGroupService.createSetGroup(setGroupDto));
    }

    @PutMapping("/setGroups/{setGroupId}")
    public ResponseEntity<SetGroupDto> putSetGroup(@PathVariable final Long setGroupId, @RequestBody final SetGroupDto setGroupDto) throws IllegalAccessException {
        if (!setGroupId.equals(setGroupDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.setGroupService.updateSetGroup(setGroupDto));
    }

    @DeleteMapping("/setGroups/{setGroupId}")
    public ResponseEntity<Void> deleteSetGroup(@PathVariable final Long setGroupId) throws IllegalAccessException {
        this.setGroupService.deleteSetGroup(setGroupId);
        return ResponseEntity.ok().build();
    }

}
