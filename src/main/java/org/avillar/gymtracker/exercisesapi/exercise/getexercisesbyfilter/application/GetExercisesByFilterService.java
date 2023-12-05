package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.GetExercisesFilter;

public interface GetExercisesByFilterService {

  List<Exercise> execute(GetExercisesFilter getExercisesByFilterRequestApplication)
      throws ExerciseIllegalAccessException;
}
