package org.avillar.gymtracker.workoutapi.workout.application.get.workout;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponseFacade;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.workout.application.get.workout.mapper.GetWorkoutServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.application.get.workout.model.GetWorkoutResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.springframework.stereotype.Service;

// FINALIZAR
@Service
@RequiredArgsConstructor
public class GetWorkoutServiceImpl implements GetWorkoutService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetWorkoutServiceMapper getWorkoutServiceMapper;

  private final ExerciseRepositoryClient exerciseRepositoryClient;

  @Override
  public GetWorkoutResponseApplication getWorkout(final UUID workoutId, final boolean full) {
    final Workout workout = full ? getFullWorkout(workoutId) : getSimpleWorkout(workoutId);

    authWorkoutsService.checkAccess(workout, AuthOperations.READ);

    if (!full) {
      return  getWorkoutServiceMapper.getResponse(workout);
    }

    // TODO Revisar logica exercise
    final List<GetExerciseResponseFacade> getExerciseResponsFacades =
        exerciseRepositoryClient.getExerciseByIds(
            workout.getSetGroups().stream()
                .map(SetGroup::getExerciseId)
                .collect(Collectors.toSet()));

    final GetWorkoutResponseApplication getWorkoutResponseApplication = getWorkoutServiceMapper.getResponse(workout);

    getWorkoutResponseApplication
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

    return getWorkoutResponseApplication; // FIXME Arreglar mapeo no full
  }

  private Workout getFullWorkout(final UUID workoutId) {
    return workoutDao.getFullWorkoutByIds(List.of(workoutId)).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
  }

  private Workout getSimpleWorkout(final UUID workoutId) {
    return workoutDao
        .findById(workoutId)
        .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
  }
}
