package org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.infrastructure.mapper;

import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.application.model.IncrementExerciseUsesResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.incrementexerciseuses.infrastructure.model.IncrementExerciseUsesResponseInfrastructure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IncrementExerciseUsesControllerMapper {

  IncrementExerciseUsesResponseInfrastructure map(
      IncrementExerciseUsesResponseApplication incrementExerciseUsesResponseApplication);
}
