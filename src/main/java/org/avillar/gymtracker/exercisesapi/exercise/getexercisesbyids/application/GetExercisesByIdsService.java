package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;

public interface GetExercisesByIdsService {

  List<GetExercisesByIdsResponseApplication> execute(Set<UUID> exerciseIds)
      throws EntityNotFoundException, IllegalAccessException;
}