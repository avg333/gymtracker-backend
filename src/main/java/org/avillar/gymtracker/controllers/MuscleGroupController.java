package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.MuscleGroupDto;
import org.avillar.gymtracker.model.dto.MuscleSubGroupDto;
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

    @Autowired
    public MuscleGroupController(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }

    @GetMapping("")
    public ResponseEntity<List<MuscleGroupDto>> getAllMuscleGroups() {
        return ResponseEntity.ok(this.muscleGroupService.getAllMuscleGroups());
    }

    @GetMapping("/{muscleGroupId}/muscleSubGroups")
    public ResponseEntity<List<MuscleSubGroupDto>> getMuscleSubgroups(@PathVariable final Long muscleGroupId) {
        return ResponseEntity.ok(this.muscleGroupService.getAllMuscleGroupMuscleSubGroups(muscleGroupId));
    }
}
