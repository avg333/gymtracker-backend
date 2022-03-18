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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @Autowired
    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @GetMapping("/muscleGroups")
    public ResponseEntity<List<MuscleGroupDto>> getAllMuscleGroups() {
        final List<MuscleGroup> muscleGroups = this.exerciseService.getAllMuscleGroups();
        final List<MuscleGroupDto> muscleGroupDtos = new ArrayList<>();
        for (final MuscleGroup muscleGroup : muscleGroups) {
            MuscleGroupDto muscleGroupDto = new MuscleGroupDto(muscleGroup.getId(), muscleGroup.getName(), muscleGroup.getDescription());
            muscleGroupDtos.add(muscleGroupDto);
        }
        return muscleGroupDtos.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(muscleGroupDtos, HttpStatus.OK);
    }

    @GetMapping("/loadType")
    public ResponseEntity<List<LoadTypeDto>> getAllLoadTypes() {
        final List<LoadType> loadTypes = this.exerciseService.getAllLoadTypes();
        final List<LoadTypeDto> loadTypesDto = new ArrayList<>();
        for (final LoadType loadType : loadTypes) {
            final LoadTypeDto loadTypeDto = new LoadTypeDto(loadType.getId(), loadType.getName(), loadType.getDescription());
            loadTypesDto.add(loadTypeDto);
        }
        return loadTypesDto.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(loadTypesDto, HttpStatus.OK);
    }

    @GetMapping("/muscleSubGroup")
    public ResponseEntity<List<MuscleSubGroupDto>> getMuscleSubGroupByFilter(@RequestParam Long idMuscleGroup) {
        final MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setId(idMuscleGroup);

        final List<MuscleSubGroup> muscleSubGroups = this.exerciseService.getMuscleSubgroupsByMuscleGroup(muscleGroup);

        final List<MuscleSubGroupDto> muscleSubGroupDtos = new ArrayList<>();
        for (final MuscleSubGroup muscleSubGroup : muscleSubGroups) {
            final MuscleSubGroupDto muscleGroupDto = new MuscleSubGroupDto(muscleSubGroup.getId(),
                    muscleSubGroup.getName(), muscleSubGroup.getDescription());
            muscleSubGroupDtos.add(muscleGroupDto);
        }

        return muscleSubGroupDtos.isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(muscleSubGroupDtos, HttpStatus.OK);
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
                new ExerciseDto(exercise.getId(), exercise.getName(), exercise.getDescription(),
                        exercise.getUnilateral(), new ArrayList<>(), exercise.getLoadType().getId()), HttpStatus.OK);
    }

    @PostMapping("/exercise")
    public ResponseEntity<Exercise> addExercise(final ExerciseDto exerciseDto) {
        Exercise exercise = new Exercise();
        exercise.setName(exerciseDto.name());
        exercise.setDescription(exerciseDto.description());
        exercise.setUnilateral(exerciseDto.unilateral());
        final LoadType loadType = new LoadType();
        loadType.setId(1L);
        exercise.setLoadType(loadType);
        final Set<MuscleGroup> muscleGroups = new LinkedHashSet<>() {};
        final MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setId(5L);
        muscleGroups.add(muscleGroup);
        exercise.setMuscleGroups(muscleGroups);

        exercise = this.exerciseService.addExercise(exercise);

        return exercise == null ? new ResponseEntity<>(HttpStatus.UPGRADE_REQUIRED)
                : new ResponseEntity<>(exercise, HttpStatus.CREATED);
    }

    @PutMapping("/exercise/{id}")
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable Long id, @RequestParam ExerciseDto exerciseDto) {
        Exercise exercise = new Exercise();
        exercise.setId(id);
        exercise.setName(exerciseDto.name());
        exercise.setDescription(exerciseDto.description());
        exercise.setUnilateral(exerciseDto.unilateral());
        exercise = this.exerciseService.updateExercise(exercise);

        return exercise == null
                ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
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

        final List<ExerciseDto> exerciseDtos = new ArrayList<>();

        for (final Exercise exercise : exercises) {
            final ExerciseDto exerciseDto = new ExerciseDto(exercise.getId(), exercise.getName(), exercise.getDescription(),
                    exercise.getUnilateral(), List.of(1L), exercise.getLoadType().getId());
            exerciseDtos.add(exerciseDto);
        }

        return new ResponseEntity<>(exerciseDtos, HttpStatus.OK);
    }
}
