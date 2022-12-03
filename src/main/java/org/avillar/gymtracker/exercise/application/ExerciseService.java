package org.avillar.gymtracker.exercise.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseFilterDto;

import java.util.List;

public interface ExerciseService {

    List<ExerciseDto> getAllExercises(ExerciseFilterDto exerciseFilterDto);

    ExerciseDto getExercise(Long exerciseId) throws EntityNotFoundException, IllegalAccessException;

    ExerciseDto createExercise(ExerciseDto exerciseDto) throws EntityNotFoundException;

    ExerciseDto updateExercise(ExerciseDto exerciseDto) throws EntityNotFoundException, IllegalAccessException;

    void deleteExercise(Long exerciseId) throws EntityNotFoundException, IllegalAccessException;
}