package org.avillar.gymtracker.workoutapi.common.facade.exercise;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.model.DeleteExerciseUsesRequestFacade;

public interface ExercisesFacade {

  void checkExerciseAccessById(UUID exerciseId) throws ExerciseUnavailableException;

  List<Exercise> getExerciseByIds(Set<UUID> exerciseIds) throws ExerciseUnavailableException;

  int incrementExerciseUses(UUID userId, UUID exerciseId) throws ExerciseUnavailableException;

  int decrementExerciseUses(UUID userId, UUID exerciseId) throws ExerciseUnavailableException;

  void decrementExercisesUses(
      UUID userId, DeleteExerciseUsesRequestFacade deleteExerciseUsesRequestFacade)
      throws ExerciseUnavailableException;

  int swapExerciseUses(UUID userId, UUID newExerciseId, UUID oldExerciseId)
      throws ExerciseUnavailableException;
}
