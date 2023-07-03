package org.avillar.gymtracker.workoutapi.workout.createworkout.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutRequestApplication;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.model.CreateWorkoutResponseApplication;

public interface CreateWorkoutService {

  CreateWorkoutResponseApplication execute(
      UUID userId, CreateWorkoutRequestApplication createWorkoutRequestApplication)
      throws IllegalAccessException, DuplicatedWorkoutDateException;
}
