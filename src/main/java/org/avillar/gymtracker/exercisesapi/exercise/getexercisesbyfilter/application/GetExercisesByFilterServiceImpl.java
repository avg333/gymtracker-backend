package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.ExerciseFacade;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.application.model.GetExercisesByFilterRequestApplication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetExercisesByFilterServiceImpl implements GetExercisesByFilterService {

  private final ExerciseFacade exerciseFacade;
  private final AuthExercisesService authExercisesService;

  @Override
  public List<Exercise> execute(final GetExercisesByFilterRequestApplication request)
      throws ExerciseIllegalAccessException {

    final UUID loggedUserId = authExercisesService.getLoggedUserId();

    // TODO Paginate and add order
    final List<Exercise> exercises =
        exerciseFacade.getExercisesByFilter(null, null); // TODO Improve this method (criteria?)

    authExercisesService.checkAccess(exercises, AuthOperations.READ);

    return exercises;
  }
}
