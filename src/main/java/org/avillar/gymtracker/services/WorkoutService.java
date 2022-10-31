package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.dto.WorkoutDto;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

public interface WorkoutService {
    List<WorkoutDto> getAllWorkouts();

    WorkoutDto getWorkout(Long workoutId) throws EntityNotFoundException, IllegalAccessException;

    List<WorkoutDto> getWorkoutByDate(Date date) throws EntityNotFoundException;

    WorkoutDto createWorkout(WorkoutDto workoutDto) throws EntityNotFoundException;

    WorkoutDto updateWorkout(WorkoutDto workoutDto) throws EntityNotFoundException, IllegalAccessException;

    void deleteWorkout(Long workoutId) throws EntityNotFoundException, IllegalAccessException;
}
