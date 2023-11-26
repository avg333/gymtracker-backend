package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterRequestApplication;

public interface GetExercisesByFilterService {

  List<Exercise> execute(
      GetExercisesByFilterRequestApplication getExercisesByFilterRequestApplication)
      throws ExerciseIllegalAccessException;
}
