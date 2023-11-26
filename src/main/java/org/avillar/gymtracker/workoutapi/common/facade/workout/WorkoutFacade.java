package org.avillar.gymtracker.workoutapi.common.facade.workout;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutNotFoundException;

public interface WorkoutFacade {

  boolean existsWorkoutByUserAndDate(UUID userId, LocalDate date);

  Map<LocalDate, UUID> getWorkoutsIdAndDatesByUser(UUID userId);

  Map<LocalDate, UUID> getWorkoutsIdAndDatesByUserAndExercise(UUID userId, UUID exerciseId);

  Workout getWorkout(UUID workoutId) throws WorkoutNotFoundException;

  Workout getWorkoutWithSetGroups(UUID workoutId) throws WorkoutNotFoundException;

  Workout getFullWorkout(UUID workoutId) throws WorkoutNotFoundException;

  List<Workout> getFullWorkoutsByIds(List<UUID> workoutIds);

  Workout saveWorkout(Workout workout);

  void deleteWorkout(UUID workoutId);
}
