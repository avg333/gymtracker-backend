package org.avillar.gymtracker.workoutapi.exercise.application.facade;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;

public interface ExerciseRepositoryClient {

  void checkExerciseAccessById(UUID exerciseId)
      throws ExerciseNotFoundException, IllegalAccessException;

  List<GetExerciseResponseFacade> getExerciseByIds(Set<UUID> exerciseIds)
      throws IllegalAccessException;
}
