package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.mapper.GetWorkoutDetailsServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutDetailsResponseApplication;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetWorkoutDetailsServiceImpl implements GetWorkoutDetailsService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetWorkoutDetailsServiceMapper getWorkoutDetailsServiceMapper;
  private final ExerciseRepositoryClient exerciseRepositoryClient;

  private static void mapExercises(
      final List<GetExerciseResponseFacade> getExerciseResponseFacades,
      final GetWorkoutDetailsResponseApplication getWorkoutDetailsResponseApplication) {
    getWorkoutDetailsResponseApplication
        .getSetGroups()
        .forEach(
            setGroup ->
                setGroup.setExercise(
                    getExerciseResponseFacades.stream()
                        .filter(ex -> ex.getId().equals(setGroup.getExerciseId()))
                        .findAny()
                        .orElse(null)));
  }

  @Override
  public GetWorkoutDetailsResponseApplication execute(final UUID workoutId) {
    final Workout workout = getFullWorkout(workoutId);

    authWorkoutsService.checkAccess(workout, AuthOperations.READ);

    final List<GetExerciseResponseFacade> getExerciseResponseFacades =
        getExercisesFromRepository(
            workout.getSetGroups().stream()
                .map(SetGroup::getExerciseId)
                .collect(Collectors.toSet()));

    final GetWorkoutDetailsResponseApplication getWorkoutDetailsResponseApplication =
        getWorkoutDetailsServiceMapper.map(workout);

    mapExercises(getExerciseResponseFacades, getWorkoutDetailsResponseApplication);

    return getWorkoutDetailsResponseApplication; // TODO Avoid return msupg and more
  }

  private List<GetExerciseResponseFacade> getExercisesFromRepository(final Set<UUID> exerciseIds) {
    try {
      return exerciseRepositoryClient.getExerciseByIds(exerciseIds);
    } catch (Exception e) {
      log.error("Error retrieving exercises from the repository. Ids: {}", exerciseIds, e);
    }
    return Collections.emptyList();
  }

  private Workout getFullWorkout(final UUID workoutId) {
    return workoutDao.getFullWorkoutByIds(List.of(workoutId)).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
  }
}
