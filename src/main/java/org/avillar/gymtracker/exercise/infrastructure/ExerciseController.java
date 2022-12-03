package org.avillar.gymtracker.exercise.infrastructure;

import org.avillar.gymtracker.enums.domain.LoadTypeEnum;
import org.avillar.gymtracker.exercise.application.ExerciseService;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseFilterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("")
    public ResponseEntity<List<ExerciseDto>> getAllExercises(
            @RequestParam(required = false) final String name,
            @RequestParam(required = false) final String description,
            @RequestParam(required = false) final Boolean unilateral,
            @RequestParam(required = false) final LoadTypeEnum loadType,
            @RequestParam(required = false) final List<Long> muscleSupGroupIds,
            @RequestParam(required = false) final Long muscleGroupIds,
            @RequestParam(required = false) final List<Long> muscleSubGroupIds
    ) {
        final ExerciseFilterDto exerciseFilterDto = new ExerciseFilterDto();
        exerciseFilterDto.setName(name);
        exerciseFilterDto.setDescription(description);
        exerciseFilterDto.setUnilateral(unilateral);
        exerciseFilterDto.setLoadType(loadType);
        exerciseFilterDto.setMuscleSupGroupIds(muscleSupGroupIds);
        exerciseFilterDto.setMuscleGroupIds(muscleGroupIds != null ? List.of(muscleGroupIds) : null);
        exerciseFilterDto.setMuscleSubGroupIds(muscleSubGroupIds);

        return ResponseEntity.ok(this.exerciseService.getAllExercises(exerciseFilterDto));
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable final Long exerciseId) {
        return ResponseEntity.ok(this.exerciseService.getExercise(exerciseId));
    }

    @PostMapping("")
    public ResponseEntity<ExerciseDto> postExercise(@RequestBody final ExerciseDto exerciseDto) {
        exerciseDto.setId(null);
        return ResponseEntity.ok(this.exerciseService.createExercise(exerciseDto));
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDto> putExercise(@PathVariable final Long exerciseId, @RequestBody final ExerciseDto exerciseDto) {
        exerciseDto.setId(exerciseId);
        return ResponseEntity.ok(this.exerciseService.updateExercise(exerciseDto));
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable final Long exerciseId) {
        this.exerciseService.deleteExercise(exerciseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}