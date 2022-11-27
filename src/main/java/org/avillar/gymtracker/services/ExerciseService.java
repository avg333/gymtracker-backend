package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.dto.ExerciseDto;
import org.avillar.gymtracker.model.dto.ExerciseFilterDto;

import java.util.List;

public interface ExerciseService {

    List<ExerciseDto> getAllExercises(ExerciseFilterDto exerciseFilterDto);

    ExerciseDto getExercise(Long exerciseId);

    ExerciseDto createExercise(ExerciseDto exerciseDto);

    ExerciseDto updateExercise(ExerciseDto exerciseDto);

    void deleteExercise(Long exerciseId);
}