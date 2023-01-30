package org.avillar.gymtracker.exercise.application;

import org.avillar.gymtracker.base.application.IncorrectFormException;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseFilterDto;

import java.util.List;
import java.util.Map;

public interface ExerciseService {

    List<ExerciseDto> getAllExercises(ExerciseFilterDto exerciseFilterDto);

    ExerciseDto getExercise(Long exerciseId) throws EntityNotFoundException, IllegalAccessException;

    ExerciseDto createExercise(ExerciseDto exerciseDto, final Map<String, String> errorMap) throws EntityNotFoundException, IncorrectFormException;

    ExerciseDto updateExercise(ExerciseDto exerciseDto, final Map<String, String> errorMap) throws EntityNotFoundException, IllegalAccessException, IncorrectFormException;

    void deleteExercise(Long exerciseId) throws EntityNotFoundException, IllegalAccessException;
}