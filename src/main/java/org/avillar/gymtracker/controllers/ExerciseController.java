package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.dto.*;
import org.avillar.gymtracker.model.Exercise;
import org.avillar.gymtracker.model.LoadType;
import org.avillar.gymtracker.model.MuscleGroup;
import org.avillar.gymtracker.model.MuscleSubGroup;
import org.avillar.gymtracker.services.ExerciseService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ExerciseController {

    private final ExerciseService exerciseService;
    private final ModelMapper modelMapper;

    @Autowired
    public ExerciseController(ExerciseService exerciseService, ModelMapper modelMapper) {
        this.exerciseService = exerciseService;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @GetMapping("muscleGroups")
    public ResponseEntity<List<MuscleGroupDto>> getAllMuscleGroups() {
        final List<MuscleGroup> muscleGroups = this.exerciseService.getAllMuscleGroups();
        return ResponseEntity.ok(muscleGroups.stream().map(muscleGroup -> modelMapper.map(muscleGroup, MuscleGroupDto.class)).toList());
    }

    @GetMapping("loadTypes")
    public ResponseEntity<List<LoadTypeDto>> getAllLoadTypes() {
        final List<LoadType> loadTypes = this.exerciseService.getAllLoadTypes();
        return ResponseEntity.ok(loadTypes.stream().map(loadType -> modelMapper.map(loadType, LoadTypeDto.class)).toList());
    }

    @GetMapping("muscleGroups/{id}/muscleSubGroups")
    public ResponseEntity<List<MuscleSubGroupDto>> getMuscleSubGroupByFilter(@PathVariable Long id) {
        final MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setId(id);

        final List<MuscleSubGroup> muscleSubGroups = this.exerciseService.getMuscleSubgroupsByMuscleGroup(muscleGroup);
        return ResponseEntity.ok(muscleSubGroups.stream().map(muscleSubGroup -> modelMapper.map(muscleSubGroup, MuscleSubGroupDto.class)).toList());
    }

    @GetMapping("exercises")
    public ResponseEntity<List<ExerciseOutDto>> getAllExercises() {
        final List<Exercise> exercises = this.exerciseService.getAllExercises();
        final List<ExerciseOutDto> exerciseDtos = new ArrayList<>();
        for (final Exercise exercise : exercises) {
            final ExerciseOutDto exerciseDto = new ExerciseOutDto(exercise.getId(), exercise.getName(), exercise.getDescription(),
                    exercise.getUnilateral(), exercise.getLoadType().getName(), null, null);
            exerciseDtos.add(exerciseDto);
        }

        return ResponseEntity.ok(exerciseDtos);
    }

    @GetMapping("exercise")
    public ResponseEntity<List<ExerciseOutDto>> getExercisesWithFilter(@RequestParam(required = false) Long idMg,
                                                                       @RequestParam(required = false) Long idLt,
                                                                       @RequestParam(required = false) Boolean uni) {
        final LoadType loadType = null;
        final List<Exercise> exercises = new ArrayList<>();//this.exerciseService.getExercisesByFilters();

        return ResponseEntity.ok(exercises.stream().map(exercise -> modelMapper.map(exercise, ExerciseOutDto.class)).toList());
    }

    @GetMapping("exercises/{id}")
    public ResponseEntity<ExerciseOutDto> getExerciseById(@PathVariable Long id) {
        final Exercise exercise = this.exerciseService.getExerciseById(id);
        return ResponseEntity.ok(modelMapper.map(exercise, ExerciseOutDto.class));
    }

    @PostMapping("exercises")
    public ResponseEntity<Exercise> addExercise(final ExerciseInDto exerciseDto) {
        Exercise exercise = modelMapper.map(exerciseDto, Exercise.class);
        exercise.setId(null);
        exercise = this.exerciseService.addExercise(exercise);

        return exercise == null ? new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED)
                : new ResponseEntity<>(exercise, HttpStatus.CREATED);
    }

    @PutMapping("exercises/{id}")
    public ResponseEntity<ExerciseInDto> updateExercise(@PathVariable Long id, @RequestParam ExerciseInDto exerciseDto) {
        Exercise exercise = modelMapper.map(exerciseDto, Exercise.class);
        exercise.setId(id);
        exercise = this.exerciseService.updateExercise(exercise);

        return exercise == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(exerciseDto, HttpStatus.OK);
    }

    @DeleteMapping("exercises/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        return new ResponseEntity<>(this.exerciseService.deleteExercise(id)
                ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

}
