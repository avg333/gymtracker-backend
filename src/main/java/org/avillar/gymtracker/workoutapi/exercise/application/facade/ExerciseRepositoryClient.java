package org.avillar.gymtracker.workoutapi.exercise.application.facade;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;

public interface ExerciseRepositoryClient {

  void checkExerciseAccessById(UUID exerciseId) throws ExerciseNotFoundException;

  List<GetExerciseResponseFacade> getExerciseByIds(Set<UUID> exerciseIds)
      throws ExerciseNotFoundException;

  int incrementExerciseUses(UUID exerciseId) throws ExerciseNotFoundException;

  int decrementExerciseUses(UUID exerciseId) throws ExerciseNotFoundException;
}
