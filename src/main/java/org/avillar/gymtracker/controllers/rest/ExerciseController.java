package org.avillar.gymtracker.controllers.rest;

import org.avillar.gymtracker.model.dto.ExerciseDto;
import org.avillar.gymtracker.model.entities.Exercise;
import org.avillar.gymtracker.services.ExerciseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final ModelMapper modelMapper;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, ModelMapper modelMapper) {
        this.exerciseService = exerciseService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<List<ExerciseDto>> getAllExercises() {
        final List<Exercise> exercises = this.exerciseService.getAllExercises();
        final TypeToken<List<ExerciseDto>> typeToken = new TypeToken<>() {
        };
        return ResponseEntity.ok(modelMapper.map(exercises, typeToken.getType()));
    }

    @GetMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable final Long exerciseId) {
        final Exercise exercise = this.exerciseService.getExercise(exerciseId);
        return ResponseEntity.ok(modelMapper.map(exercise, ExerciseDto.class));
    }

    @PostMapping("")
    public ResponseEntity<ExerciseDto> addExercise(@RequestBody final ExerciseDto exerciseDto) {
        final Exercise exerciseInput = modelMapper.map(exerciseDto, Exercise.class);
        final Exercise exercise = this.exerciseService.createExercise(exerciseInput);
        return ResponseEntity.ok(modelMapper.map(exercise, ExerciseDto.class));
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable final Long exerciseId, @RequestBody final ExerciseDto exerciseDto) {
        final Exercise exerciseInput = modelMapper.map(exerciseDto, Exercise.class);
        final Exercise exercise = this.exerciseService.updateExercise(exerciseId, exerciseInput);
        return ResponseEntity.ok(modelMapper.map(exercise, ExerciseDto.class));
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable final Long exerciseId) {
        this.exerciseService.deleteExercise(exerciseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
