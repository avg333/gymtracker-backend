package org.avillar.gymtracker.workoutapi.workout.application.get.summary;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.auth.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsService;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.workout.application.get.summary.model.GetWorkoutSummaryResponseApplication;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetWorkoutSummarySummaryServiceImpl implements GetWorkoutSummaryService {

  private final WorkoutDao workoutDao;
  private final AuthWorkoutsService authWorkoutsService;

  @Override
  public GetWorkoutSummaryResponseApplication getWorkoutSummary(final UUID workoutId) {

    final Workout workout = getFullWorkout(workoutId);

    authWorkoutsService.checkAccess(workout, AuthOperations.READ);

    final GetWorkoutSummaryResponseApplication getWorkoutSummaryResponseApplication = new GetWorkoutSummaryResponseApplication();

    final double minRir = 3;

    final java.util.Set<UUID> exercises = new HashSet<>();
    // final Map<UUID, ExerciseDto> exercises = new HashMap<>();
    // final Map<UUID, MuscleGroupDto> muscleGroups = new HashMap<>();
    final Map<UUID, Set> efectiveSets =
        workout.getSetGroups().stream()
            .flatMap(setGroup -> setGroup.getSets().stream())
            .filter(set -> set.getRir() <= minRir)
            .collect(Collectors.toMap(Set::getId, set -> set));

    double weight = 0;
    Date startWo = null;
    Date endWo = null;
    for (final Set set : efectiveSets.values()) {
      if (startWo == null || set.getLastModifiedAt().getTime() < startWo.getTime()) {
        startWo = set.getLastModifiedAt();
      }
      if (endWo == null || set.getLastModifiedAt().getTime() > endWo.getTime()) {
        endWo = set.getLastModifiedAt();
      }

      exercises.add(set.getSetGroup().getExerciseId());
      /*
      final Exercise exercise = set.getSetGroup().getExercise();
       exercises.putIfAbsent(exercise.getId(), exerciseMapper.toDto(exercise, -1));

       for (final MuscleGroup muscleGroupSet : exercise.getMuscleGroupExercises().stream()
               .map(MuscleGroupExercise::getMuscleGroup)
               .toList()) {
           muscleGroups.putIfAbsent(muscleGroupSet.getId(), muscleGroupMapper.toDto(muscleGroupSet, true));
           muscleGroups.get(muscleGroupSet.getId()).setVolume(muscleGroups.get(muscleGroupSet.getId()).getVolume() + 1);
       }
       */

      if (set.getWeight() != null) {
        weight += set.getWeight();
      }
    }

    if (startWo != null) {
      final long duration = endWo.getTime() - startWo.getTime();
      getWorkoutSummaryResponseApplication.setDuration((int) TimeUnit.MILLISECONDS.toMinutes(duration));
    }
    /*
    final List<MuscleGroupDto> muscleGroupDtos = new ArrayList<>(muscleGroups.values());
    muscleGroupDtos.sort(Comparator.comparing(MuscleGroupDto::getVolume).reversed());
    getWorkoutSummaryResponse.setMuscleGroupDtos(muscleGroupDtos);
    */

    getWorkoutSummaryResponseApplication.setExerciseNumber(exercises.size());
    getWorkoutSummaryResponseApplication.setSetsNumber(efectiveSets.size());
    getWorkoutSummaryResponseApplication.setWeightVolume((int) weight);

    return getWorkoutSummaryResponseApplication;
  }

  private Workout getFullWorkout(final UUID workoutId) {
    return workoutDao.getFullWorkoutByIds(List.of(workoutId)).stream()
        .findAny()
        .orElseThrow(() -> new EntityNotFoundException(Workout.class, workoutId));
  }
}
