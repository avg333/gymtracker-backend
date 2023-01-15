package org.avillar.gymtracker.workout.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface WorkoutService {

    List<WorkoutDto> getAllUserWorkouts(Long userId) throws EntityNotFoundException, IllegalAccessException;

    WorkoutDto getWorkout(Long workoutId) throws EntityNotFoundException, IllegalAccessException;

    WorkoutDto createWorkout(WorkoutDto workoutDto) throws EntityNotFoundException, IllegalAccessException;

    /**
     * Modify the workout with the specified id in workoutDto
     *
     * @param workoutDto workout data to replace the previous workout data
     * @throws EntityNotFoundException if there is no workout with that id in workoutDto
     * @throws IllegalAccessException  if the logged-in user does not have permission to modify the workout
     */
    WorkoutDto updateWorkout(WorkoutDto workoutDto) throws EntityNotFoundException, IllegalAccessException;

    /**
     * Delete the workout with the specified id
     *
     * @param workoutId workout id to delete
     * @throws EntityNotFoundException if there is no workout with that id
     * @throws IllegalAccessException  if the logged-in user does not have permission to delete the workout
     */
    void deleteWorkout(Long workoutId) throws EntityNotFoundException, IllegalAccessException;

    WorkoutDto addSetGroupsToWorkoutFromWorkout(Long workoutDestinationId, Long workoutSourceId) throws IllegalAccessException;

    WorkoutDto addSetGroupsToWorkoutFromSession(Long workoutDestinationId, Long sessionSourceId) throws IllegalAccessException;
}
