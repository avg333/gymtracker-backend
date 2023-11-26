package org.avillar.gymtracker.exercisesapi.common.facade.exercise;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.exception.application.ExerciseNotFoundException;

public interface ExerciseFacade {

  Exercise getExerciseById(final UUID exerciseId) throws ExerciseNotFoundException;

  List<Exercise> getExercisesByFilter(UUID userId, GetExercisesFilter filter);

  List<Exercise> getExercisesByIds(final Collection<UUID> exerciseIds);

  Exercise getFullExerciseById(final UUID exerciseId) throws ExerciseNotFoundException;

  Exercise getExerciseWithMuscleGroupExUses(final UUID exerciseId) throws ExerciseNotFoundException;

  void deleteExerciseById(final UUID exerciseId);
}
