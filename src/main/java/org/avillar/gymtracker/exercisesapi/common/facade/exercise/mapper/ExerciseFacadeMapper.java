package org.avillar.gymtracker.exercisesapi.common.facade.exercise.mapper;

import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseSearchCriteria;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.GetExercisesFilter;

public interface ExerciseFacadeMapper {

  ExerciseEntity map(Exercise exercise);

  ExerciseSearchCriteria map(UUID userId, GetExercisesFilter getExercisesFilter);
}
