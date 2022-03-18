package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.dto.ExerciseDto;
import org.avillar.gymtracker.dto.LoadTypeDto;
import org.avillar.gymtracker.dto.MuscleGroupDto;
import org.avillar.gymtracker.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.model.Exercise;
import org.avillar.gymtracker.model.LoadType;
import org.avillar.gymtracker.model.MuscleGroup;
import org.avillar.gymtracker.model.MuscleSubGroup;
import org.avillar.gymtracker.services.ExerciseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    }

    @GetMapping("/muscleGroups")
    public ResponseEntity<List<MuscleGroupDto>> getAllMuscleGroups() {
        final List<MuscleGroup> muscleGroups = this.exerciseService.getAllMuscleGroups();
        return muscleGroups.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(muscleGroups.stream().map(muscleGroup -> modelMapper.map(muscleGroup, MuscleGroupDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("/loadType")
    public ResponseEntity<List<LoadTypeDto>> getAllLoadTypes() {
        final List<LoadType> loadTypes = this.exerciseService.getAllLoadTypes();
        return loadTypes.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(loadTypes.stream().map(loadType -> modelMapper.map(loadType, LoadTypeDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("/muscleSubGroup")
    public ResponseEntity<List<MuscleSubGroupDto>> getMuscleSubGroupByFilter(@RequestParam Long idMuscleGroup) {
        final MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setId(idMuscleGroup);

        final List<MuscleSubGroup> muscleSubGroups = this.exerciseService.getMuscleSubgroupsByMuscleGroup(muscleGroup);

        return muscleSubGroups.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(muscleSubGroups.stream().map(muscleSubGroup -> modelMapper.map(muscleSubGroup, MuscleSubGroupDto.class)).toList(), HttpStatus.OK);
    }

    @GetMapping("/exercises")
    public ResponseEntity<List<ExerciseDto>> getAllExercises() {
        final List<Exercise> exercises = this.exerciseService.getAllExercises();
        return getListResponseEntity(exercises);
    }

    @GetMapping("/exercise")
    public ResponseEntity<List<ExerciseDto>> getExercisesWithFilter(@RequestParam(required = false) Long idMg,
                                                                    @RequestParam(required = false) Long idLt,
                                                                    @RequestParam(required = false) Boolean uni) {
        final LoadType loadType = null;
        final List<Exercise> exercises = this.exerciseService.getExercisesByFilters();

        return getListResponseEntity(exercises);
    }

    @GetMapping("/exercise/{id}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable Long id) {
        final Exercise exercise = this.exerciseService.getExerciseById(id);

        return exercise == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND) : new ResponseEntity<>(
                modelMapper.map(exercise, ExerciseDto.class), HttpStatus.OK);
    }

    @PostMapping("/exercise")
    public ResponseEntity<Exercise> addExercise(final ExerciseDto exerciseDto) {
        Exercise exercise = modelMapper.map(exerciseDto, Exercise.class);

        exercise = this.exerciseService.addExercise(exercise);

        return exercise == null ? new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED)
                : new ResponseEntity<>(exercise, HttpStatus.CREATED);
    }

    @PutMapping("/exercise/{id}")
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable Long id, @RequestParam ExerciseDto exerciseDto) {
        Exercise exercise = modelMapper.map(exerciseDto, Exercise.class);
        exercise.setId(id);
        exercise = this.exerciseService.updateExercise(exercise);

        return exercise == null ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                : new ResponseEntity<>(exerciseDto, HttpStatus.OK);
    }

    @DeleteMapping("/exercise/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        return new ResponseEntity<>(this.exerciseService.deleteExercise(id)
                ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<List<ExerciseDto>> getListResponseEntity(List<Exercise> exercises) {
        if (exercises.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(exercises.stream().map(exercise -> modelMapper.map(exercise, ExerciseDto.class)).toList(), HttpStatus.OK);
    }
}
