package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.mapper.GetWorkoutDetailsServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.application.model.GetWorkoutDetailsResponseApplication;
import org.springframework.stereotype.Service;

// FINALIZAR
@Service
@RequiredArgsConstructor
public class GetWorkoutDetailsServiceImpl implements GetWorkoutDetailsService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetWorkoutDetailsServiceMapper getWorkoutDetailsServiceMapper;
  private final ExerciseRepositoryClient exerciseRepositoryClient;

  @Override
  public GetWorkoutDetailsResponseApplication execute(final UUID workoutId) {
    final Workout workout = getFullWorkout(workoutId);

    authWorkoutsService.checkAccess(workout, AuthOperations.READ);

    // TODO Revisar logica exercise
    final List<GetExerciseResponseFacade> getExerciseResponsFacades =
        exerciseRepositoryClient.getExerciseByIds(
            workout.getSetGroups().stream()
                .map(SetGroup::getExerciseId)
                .collect(Collectors.toSet()));

    final GetWorkoutDetailsResponseApplication getWorkoutDetailsResponseApplication =
        getWorkoutDetailsServiceMapper.map(workout);

    getWorkoutDetailsResponseApplication
        .getSetGroups()
        .forEach(
            setGroup ->
                setGroup.setExercise(
                    getExerciseResponsFacades.stream()
                        .filter(
                            getExerciseResponse ->
                                getExerciseResponse.getId().equals(setGroup.getExerciseId()))
                        .findAny()
                        .orElseThrow( // Esto nunca deberia saltar
                            () ->
                                new EntityNotFoundException(
                                    Exercise.class, setGroup.getExerciseId()))));

    return getWorkoutDetailsResponseApplication;
  }

  private Workout getFullWorkout(final UUID workoutId) {
    return workoutDao.getFullWorkoutByIds(List.of(workoutId)).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
  }
}
