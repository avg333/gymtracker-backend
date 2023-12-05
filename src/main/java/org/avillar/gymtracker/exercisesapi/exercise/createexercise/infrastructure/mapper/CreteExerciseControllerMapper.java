package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.mapper;

import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseRequest;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreteExerciseControllerMapper {

  Exercise map(CreateExerciseRequest createExerciseRequest);

  CreateExerciseResponse map(Exercise createExerciseResponse);
}
