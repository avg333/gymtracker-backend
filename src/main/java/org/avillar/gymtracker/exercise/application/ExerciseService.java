package org.avillar.gymtracker.exercise.application;

import java.util.List;

public interface ExerciseService {

    List<ExerciseDto> getAllExercises(ExerciseFilterDto exerciseFilterDto);

    ExerciseDto getExercise(Long exerciseId);

    ExerciseDto createExercise(ExerciseDto exerciseDto);

    ExerciseDto updateExercise(ExerciseDto exerciseDto);

    void deleteExercise(Long exerciseId);
}