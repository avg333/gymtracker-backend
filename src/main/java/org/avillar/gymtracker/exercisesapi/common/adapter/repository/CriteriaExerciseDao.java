package org.avillar.gymtracker.exercisesapi.common.adapter.repository;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseSearchCriteria;

public interface CriteriaExerciseDao {

  List<ExerciseEntity> getAllFullExercises(ExerciseSearchCriteria criteria);
}
