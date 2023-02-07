package org.avillar.gymtracker.exercise.application;

import org.avillar.gymtracker.errors.application.exceptions.BadFormException;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseFilterDto;

import java.util.List;
import java.util.Map;

public interface ExerciseService {

    List<ExerciseDto> getAllExercises(ExerciseFilterDto exerciseFilterDto);

    ExerciseDto getExercise(Long exerciseId) throws EntityNotFoundException, IllegalAccessException;

    ExerciseDto createExercise(ExerciseDto exerciseDto, final Map<String, String> errorMap) throws EntityNotFoundException, BadFormException;

    ExerciseDto updateExercise(ExerciseDto exerciseDto, final Map<String, String> errorMap) throws EntityNotFoundException, IllegalAccessException, BadFormException;

    void deleteExercise(Long exerciseId) throws EntityNotFoundException, IllegalAccessException;
}