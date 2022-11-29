package org.avillar.gymtracker.musclegroup.infrastructure;

import org.avillar.gymtracker.musclegroup.application.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.MuscleGroupService;
import org.avillar.gymtracker.musclegroup.application.MuscleSubGroupDto;
import org.avillar.gymtracker.musclegroup.application.MuscleSupGroupDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class MuscleGroupController {

    private final MuscleGroupService muscleGroupService;

    @Autowired
    public MuscleGroupController(MuscleGroupService muscleGroupService) {
        this.muscleGroupService = muscleGroupService;
    }

    @GetMapping("muscleSupGroups")
    public ResponseEntity<List<MuscleSupGroupDto>> getAllMuscleSupGroups() {
        return ResponseEntity.ok(this.muscleGroupService.getAllMuscleSupGroups());
    }

    @GetMapping("muscleSupGroups/{muscleSupGroupId}")
    public ResponseEntity<MuscleSupGroupDto> getMuscleSupGroup(@PathVariable final Long muscleSupGroupId) {
        return ResponseEntity.ok(this.muscleGroupService.getMuscleSupGroup(muscleSupGroupId));
    }

    @GetMapping("muscleSupGroups/{muscleSupGroupId}/muscleGroups")
    public ResponseEntity<List<MuscleGroupDto>> getAllMuscleGroups(@PathVariable final Long muscleSupGroupId) {
        return ResponseEntity.ok(this.muscleGroupService.getAllMuscleGroups(muscleSupGroupId));
    }

    @GetMapping("muscleGroups/{muscleGroupId}")
    public ResponseEntity<MuscleGroupDto> getMuscleGroup(@PathVariable final Long muscleGroupId) {
        return ResponseEntity.ok(this.muscleGroupService.getMuscleGroup(muscleGroupId));
    }

    @GetMapping("muscleGroups/{muscleGroupId}/muscleSubGroups")
    public ResponseEntity<List<MuscleSubGroupDto>> getMuscleSubgroups(@PathVariable final Long muscleGroupId) {
        return ResponseEntity.ok(this.muscleGroupService.getAllMuscleGroupMuscleSubGroups(muscleGroupId));
    }
}
