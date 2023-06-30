package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application;

import java.util.List;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterResponseApplication;

public interface GetExercisesByFilterService {

  List<GetExercisesByFilterResponseApplication> execute(
      GetExercisesByFilterRequestApplication getExercisesByFilterRequestApplication)
      throws IllegalAccessException;
}
