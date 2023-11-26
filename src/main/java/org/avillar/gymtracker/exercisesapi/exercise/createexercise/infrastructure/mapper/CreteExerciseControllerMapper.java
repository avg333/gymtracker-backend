package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.mapper;

import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseRequest;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseResponse;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseDto.SetGroup.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CreteExerciseControllerMapper {

  Exercise map(CreateExerciseRequest createExerciseRequest);

  CreateExerciseResponse map(Exercise createExerciseResponse);
}
