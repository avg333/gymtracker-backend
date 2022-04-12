package org.avillar.gymtracker.api.controllers;

import org.avillar.gymtracker.api.dto.MuscleGroupDto;
import org.avillar.gymtracker.api.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.model.entities.MuscleGroup;
import org.avillar.gymtracker.model.entities.MuscleSubGroup;
import org.avillar.gymtracker.services.MuscleGroupService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/muscleGroups")
public class MuscleGroupController {

    private final MuscleGroupService muscleGroupService;
    private final ModelMapper modelMapper;

    @Autowired
    public MuscleGroupController(MuscleGroupService muscleGroupService, ModelMapper modelMapper) {
        this.muscleGroupService = muscleGroupService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<MuscleGroupDto>> getAllMuscleGroups() {
        final List<MuscleGroup> muscleGroups = this.muscleGroupService.getAllMuscleGroups();
        final TypeToken<List<MuscleGroupDto>> typeToken = new TypeToken<>() {
        };
        return ResponseEntity.ok(modelMapper.map(muscleGroups, typeToken.getType()));
    }

    @GetMapping("/{muscleGroupId}/muscleSubGroups")
    public ResponseEntity<List<MuscleSubGroupDto>> getMuscleSubgroups(@PathVariable final Long muscleGroupId) {
        final List<MuscleSubGroup> muscleSubGroups = this.muscleGroupService.getMuscleSubGroups(muscleGroupId);
        final TypeToken<List<MuscleSubGroupDto>> typeToken = new TypeToken<>() {
        };
        return ResponseEntity.ok(modelMapper.map(muscleSubGroups, typeToken.getType()));
    }
}
