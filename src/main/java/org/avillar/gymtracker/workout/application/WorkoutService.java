package org.avillar.gymtracker.workout.application;

import org.avillar.gymtracker.errors.application.exceptions.BadFormException;
import org.avillar.gymtracker.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface WorkoutService {

    Map<Date, Long> getAllUserWorkoutDates(Long userId) throws EntityNotFoundException, IllegalAccessException;

    Map<Date, Long> getAllUserWorkoutsWithExercise(Long userId, Long exerciseId) throws EntityNotFoundException, IllegalAccessException;

    List<WorkoutDto> getAllUserWorkoutsByDate(Long userId, Date date) throws EntityNotFoundException, IllegalAccessException;

    WorkoutDto getWorkout(Long workoutId) throws EntityNotFoundException, IllegalAccessException;

    WorkoutDto createWorkout(WorkoutDto workoutDto) throws EntityNotFoundException, BadFormException;

    WorkoutDto addSetGroupsToWorkoutFromWorkout(Long workoutDestinationId, Long workoutSourceId) throws IllegalAccessException, EntityNotFoundException;

    WorkoutDto addSetGroupsToWorkoutFromSession(Long workoutDestinationId, Long sessionSourceId) throws IllegalAccessException, EntityNotFoundException;

    /**
     * Modify the workout with the specified id in workoutDto
     *
     * @param workoutDto workout data to replace the previous workout data
     * @throws EntityNotFoundException if there is no workout with that id in workoutDto
     */
    WorkoutDto updateWorkout(WorkoutDto workoutDto) throws EntityNotFoundException, BadFormException;

    /**
     * Delete the workout with the specified id
     *
     * @param workoutId workout id to delete
     * @throws EntityNotFoundException if there is no workout with that id
     * @throws IllegalAccessException  if the logged-in user does not have permission to delete the workout
     */
    void deleteWorkout(Long workoutId) throws EntityNotFoundException, IllegalAccessException;
}
