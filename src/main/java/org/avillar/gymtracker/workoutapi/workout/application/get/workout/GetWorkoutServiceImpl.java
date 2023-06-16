package org.avillar.gymtracker.workoutapi.workout.application.get.workout;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.exercise.application.facade.ExerciseRepositoryClient;
import org.avillar.gymtracker.workoutapi.exercise.application.model.GetExerciseResponse;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.workout.application.get.workout.mapper.GetWorkoutServiceMapper;
import org.avillar.gymtracker.workoutapi.workout.application.get.workout.model.GetWorkoutResponse;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkoutServiceImpl implements GetWorkoutService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;
  private final GetWorkoutServiceMapper getWorkoutServiceMapper;

  private final ExerciseRepositoryClient exerciseRepositoryClient;

  @Override
  public GetWorkoutResponse getWorkout(final UUID workoutId, final boolean full) {
    final Workout workout = full ? getFullWorkout(workoutId) : getSimpleWorkout(workoutId);

    authWorkoutsService.checkAccess(workout, AuthOperations.READ);

    // TODO Revisar logica exercise
    final List<GetExerciseResponse> getExerciseResponses =
        exerciseRepositoryClient.getExerciseByIds(
            workout.getSetGroups().stream()
                .map(SetGroup::getExerciseId)
                .collect(Collectors.toSet()));

    final GetWorkoutResponse getWorkoutResponse = getWorkoutServiceMapper.getResponse(workout);

    getWorkoutResponse
        .getSetGroups()
        .forEach(
            setGroup ->
                setGroup.setExercise(
                    getExerciseResponses.stream()
                        .filter(
                            getExerciseResponse ->
                                getExerciseResponse.getId().equals(setGroup.getExerciseId()))
                        .findAny()
                        .get()));

    return getWorkoutResponse; // FIXME Arreglar mapeo no full
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
