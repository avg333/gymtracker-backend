package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application;

import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseDto.SetGroup.Exercise;

public interface CreateExerciseService {

  Exercise execute(UUID userId, Exercise createExerciseRequest)
      throws EntityNotFoundException, IllegalAccessException;
}
