package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.dto.ExerciseDto;
import org.avillar.gymtracker.model.Exercise;
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
        final List<ExerciseDto> exercisesDto = modelMapper.map(exercises, typeToken.getType());
        return ResponseEntity.ok(exercisesDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable Long id) {
        final Exercise exercise = this.exerciseService.getExercise(id);
        return ResponseEntity.ok(modelMapper.map(exercise, ExerciseDto.class));
    }

    @PostMapping("")
    public ResponseEntity<ExerciseDto> addExercise(final ExerciseDto exerciseDto) {
        final Exercise exerciseInput = modelMapper.map(exerciseDto, Exercise.class);
        final Exercise exercise = this.exerciseService.createExercise(exerciseInput);
        return new ResponseEntity<>(modelMapper.map(exercise, ExerciseDto.class), HttpStatus.CREATED);
    }

    @PutMapping("/{exerciseId}")
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable Long exerciseId, @RequestParam ExerciseDto exerciseDto) {
        final Exercise exerciseInput = modelMapper.map(exerciseDto, Exercise.class);
        final Exercise exercise = this.exerciseService.updateExercise(exerciseId, exerciseInput);
        return new ResponseEntity<>(modelMapper.map(exercise, ExerciseDto.class), HttpStatus.CREATED);
    }

    @DeleteMapping("/{exerciseId}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long exerciseId) {
        this.exerciseService.deleteExercise(exerciseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
