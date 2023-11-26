package org.avillar.gymtracker.exercisesapi.exercise.modifyexerciseuses.application;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseIllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.ExerciseFacade;
import org.avillar.gymtracker.exercisesapi.common.facade.exerciseuses.ExerciseUsesFacade;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModifyExerciseUsesServiceImpl implements ModifyExerciseUsesService {

  private final ExerciseFacade exerciseFacade;
  private final ExerciseUsesFacade exerciseUsesFacade;
  private final AuthExercisesService authExercisesService;

  @Override
  public List<ExerciseUses> execute(final UUID userId, final List<ExerciseUses> exerciseUses)
      throws ExerciseNotFoundException, ExerciseIllegalAccessException {

    final List<ExerciseUses> groupedExerciseUses = groupExerciseUsesByExercise(exerciseUses);

    final List<UUID> exerciseIds =
        groupedExerciseUses.stream().map(ExerciseUses::getExercise).map(Exercise::getId).toList();
    final List<Exercise> exercises = exerciseFacade.getExercisesByIds(exerciseIds);

    checkMissingExercises(groupedExerciseUses, exercises);

    authExercisesService.checkAccess(exercises, AuthOperations.READ);

    final Map<UUID, ExerciseUses> exerciseUsesMap =
        exerciseUsesFacade.getExerciseUsesByExerciseIdAndUserId(exerciseIds, userId).stream()
            .collect(Collectors.toMap(eu -> eu.getExercise().getId(), eu -> eu));

    groupedExerciseUses.forEach(eu -> populateExerciseUses(eu, exerciseUsesMap, userId));

    return exerciseUsesFacade.saveAllExerciseUses(groupedExerciseUses);
  }

  private List<ExerciseUses> groupExerciseUsesByExercise(
      final List<ExerciseUses> exerciseUsesList) {
    final Map<UUID, ExerciseUses> exerciseUsesMap = new HashMap<>();

    exerciseUsesList.forEach(
        eu -> {
          final UUID exerciseId = eu.getExercise().getId();
          if (exerciseUsesMap.containsKey(exerciseId)) {
            final ExerciseUses foundEU = exerciseUsesMap.get(exerciseId);
            foundEU.setUses(foundEU.getUses() + eu.getUses());
          } else {
            exerciseUsesMap.put(exerciseId, eu);
          }
        });

    return exerciseUsesMap.values().stream().toList();
  }

  private void checkMissingExercises(
      final List<ExerciseUses> exerciseUses, final List<Exercise> exercises)
      throws ExerciseNotFoundException {
    final List<UUID> exerciseIdsFound =
        Optional.ofNullable(exercises).orElse(Collections.emptyList()).stream()
            .map(Exercise::getId)
            .toList();
    final List<UUID> totalExerciseIds =
        exerciseUses.stream().map(ExerciseUses::getExercise).map(Exercise::getId).toList();

    if (totalExerciseIds.size() != exerciseIdsFound.size()) {
      final List<UUID> missingExerciseIds =
          totalExerciseIds.stream()
              .filter(exerciseId -> !exerciseIdsFound.contains(exerciseId))
              .toList();
      throw new ExerciseNotFoundException(missingExerciseIds.get(0)); // TODO Improve
    }
  }

  private void populateExerciseUses(
      final ExerciseUses eu, final Map<UUID, ExerciseUses> exerciseUsesMap, final UUID userId) {
    eu.setUserId(userId);

    final UUID exerciseId = eu.getExercise().getId();
    if (exerciseUsesMap.containsKey(exerciseId)) {
      final ExerciseUses exerciseUsesInDb = exerciseUsesMap.get(exerciseId);
      eu.setUses(getModifiedUses(exerciseUsesInDb.getUses(), eu.getUses()));
      eu.setId(exerciseUsesInDb.getId());
    }
  }

  private int getModifiedUses(final Integer uses, final int difference) {
    if (uses == null) {
      return difference;
    } else {
      return uses + difference;
    }
  }
}
