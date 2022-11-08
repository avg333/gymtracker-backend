package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.ExerciseDto;
import org.avillar.gymtracker.services.ExerciseService;
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
    public ResponseEntity<List<ExerciseDto>> getAllExercises() {
        return ResponseEntity.ok(this.exerciseService.getAllExercises());
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