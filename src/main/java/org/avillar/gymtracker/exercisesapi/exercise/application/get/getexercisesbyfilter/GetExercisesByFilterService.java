package org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.model.GetExercisesByFilterApplicationRequest;
import org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.model.GetExercisesByFilterApplicationResponse;

public interface GetExercisesByFilterService {

  List<GetExercisesByFilterApplicationResponse> execute(
      GetExercisesByFilterApplicationRequest getExercisesByFilterApplicationRequest);
}
