package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.mapper;

import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseResponseApplication;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CreateExerciseApplicationMapper {

  Exercise map(CreateExerciseRequestApplication createExerciseRequestApplication);

  CreateExerciseResponseApplication map(Exercise exercise);
}
