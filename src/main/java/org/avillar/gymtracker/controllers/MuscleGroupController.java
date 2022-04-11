package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.dto.MuscleGroupDto;
import org.avillar.gymtracker.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.model.LoadTypeEnum;
import org.avillar.gymtracker.model.MuscleGroup;
import org.avillar.gymtracker.model.MuscleSubGroup;
import org.avillar.gymtracker.services.MuscleGroupService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class MuscleGroupController {

    private final MuscleGroupService muscleGroupService;
    private final ModelMapper modelMapper;

    @Autowired
    public MuscleGroupController(MuscleGroupService muscleGroupService, ModelMapper modelMapper) {
        this.muscleGroupService = muscleGroupService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("muscleGroups")
    public ResponseEntity<List<MuscleGroupDto>> getAllMuscleGroups() {
        final List<MuscleGroup> muscleGroups = this.muscleGroupService.getAllMuscleGroups();
        final TypeToken<List<MuscleGroupDto>> typeToken = new TypeToken<>() {
        };
        final List<MuscleGroupDto> muscleGroupsDto = modelMapper.map(muscleGroups, typeToken.getType());
        return ResponseEntity.ok(muscleGroupsDto);
    }

    @GetMapping("muscleGroups/{muscleGroupId}/muscleSubGroups")
    public ResponseEntity<List<MuscleSubGroupDto>> getMuscleSubgroups(@PathVariable final Long muscleGroupId) {
        final List<MuscleSubGroup> muscleSubGroups = this.muscleGroupService.getMuscleSubGroups(muscleGroupId);
        final TypeToken<List<MuscleSubGroupDto>> typeToken = new TypeToken<>() {
        };
        final List<MuscleSubGroupDto> muscleSubGroupsDto = modelMapper.map(muscleSubGroups, typeToken.getType());
        return ResponseEntity.ok(muscleSubGroupsDto);
    }

    @GetMapping("loadTypes")
    public ResponseEntity<List<LoadTypeEnum>> getLoadTypes() {
        final List<LoadTypeEnum> loadTypes = Arrays.asList(LoadTypeEnum.values());
        return ResponseEntity.ok(loadTypes);
    }
}
