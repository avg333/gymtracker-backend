package org.avillar.gymtracker.exercise.infrastructure;

import org.avillar.gymtracker.base.application.IncorrectFormException;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.IllegalAccessException;
import org.avillar.gymtracker.exercise.application.ExerciseService;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseFilterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        final ExerciseFilterDto exerciseFilterDto = new ExerciseFilterDto(name, description, unilateral, loadType,
                muscleSupGroupIds, muscleGroupIds != null ? List.of(muscleGroupIds) : null, muscleSubGroupIds);
        return ResponseEntity.ok(this.exerciseService.getAllExercises(exerciseFilterDto));
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable final Long exerciseId)
            throws EntityNotFoundException, IllegalAccessException {
        return ResponseEntity.ok(this.exerciseService.getExercise(exerciseId));
    }

    @PostMapping("")
    public ResponseEntity<ExerciseDto> postExercise(@RequestBody final ExerciseDto exerciseDto)
            throws EntityNotFoundException, IncorrectFormException {
        final Map<String, String> errorMap = new HashMap<>();
        exerciseDto.setId(null);

        return ResponseEntity.ok(this.exerciseService.createExercise(exerciseDto, errorMap));
        //TODO Validate
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDto> putExercise(@PathVariable final Long exerciseId, @RequestBody final ExerciseDto exerciseDto)
            throws EntityNotFoundException, IllegalAccessException, IncorrectFormException {
        final Map<String, String> errorMap = new HashMap<>();
        exerciseDto.setId(exerciseId);

        return ResponseEntity.ok(this.exerciseService.updateExercise(exerciseDto, errorMap));
        //TODO Validate
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable final Long exerciseId)
            throws EntityNotFoundException, IllegalAccessException {
        this.exerciseService.deleteExercise(exerciseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}