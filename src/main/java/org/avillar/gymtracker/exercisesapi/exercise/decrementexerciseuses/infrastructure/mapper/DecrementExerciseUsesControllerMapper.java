package org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.infrastructure.mapper;

import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.infrastructure.model.DecrementExerciseUsesResponseInfrastructure;
import org.avillar.gymtracker.exercisesapi.exercise.decrementexerciseuses.application.model.DecrementExerciseUsesResponseApplication;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DecrementExerciseUsesControllerMapper {

  DecrementExerciseUsesResponseInfrastructure map(
      DecrementExerciseUsesResponseApplication decrementExerciseUsesResponseApplication);
}
