package org.avillar.gymtracker.exercisesapi.common.facade.exercise.mapper;

import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;

public interface ExerciseFacadeMapper {

  ExerciseEntity map(Exercise exercise);
}
