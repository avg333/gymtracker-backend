package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.dto.WorkoutDto;
import org.avillar.gymtracker.model.dto.WorkoutSummaryDto;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

public interface WorkoutService {
    @Transactional(readOnly = true)
    List<Date> getAllUserWorkoutsDates(Long userId) throws IllegalAccessException;

    List<WorkoutDto> getAllUserWorkouts(Long userId) throws IllegalAccessException;

    WorkoutDto getWorkout(Long workoutId) throws EntityNotFoundException, IllegalAccessException;

    List<WorkoutDto> getWorkoutByDate(Date date) throws EntityNotFoundException;

    @Transactional(readOnly = true)
    WorkoutSummaryDto getWorkoutSummary(Long workoutId) throws EntityNotFoundException, IllegalAccessException;

    WorkoutDto createWorkout(WorkoutDto workoutDto) throws EntityNotFoundException;

    WorkoutDto updateWorkout(WorkoutDto workoutDto) throws EntityNotFoundException, IllegalAccessException;

    void deleteWorkout(Long workoutId) throws EntityNotFoundException, IllegalAccessException;
}
