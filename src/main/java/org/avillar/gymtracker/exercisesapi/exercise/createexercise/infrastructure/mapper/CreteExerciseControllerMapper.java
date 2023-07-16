package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.mapper;

import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseRequest;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CreteExerciseControllerMapper {

  CreateExerciseRequestApplication map(CreateExerciseRequest createExerciseRequest);

  CreateExerciseResponse map(CreateExerciseResponseApplication createExerciseResponseApplication);
}
