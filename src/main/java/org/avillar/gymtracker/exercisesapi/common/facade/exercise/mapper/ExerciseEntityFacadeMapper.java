package org.avillar.gymtracker.exercisesapi.common.facade.exercise.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;

public interface ExerciseEntityFacadeMapper {

  List<Exercise> map(List<ExerciseEntity> exerciseEntities);

  Exercise map(ExerciseEntity exerciseEntity);
}
