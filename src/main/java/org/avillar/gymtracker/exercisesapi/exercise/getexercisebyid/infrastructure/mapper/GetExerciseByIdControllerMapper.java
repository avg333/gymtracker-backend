package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.mapper;

import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponse;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model.GetExerciseByIdResponse.MuscleGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetExerciseByIdControllerMapper {

  @Mapping(
      source = "muscleGroupExercises",
      target = "muscleGroups",
      qualifiedByName = "mapMuscleGroups")
  GetExerciseByIdResponse map(Exercise exercise);

  @Named("mapMuscleGroups")
  @Mapping(source = "muscleGroup.name", target = "name")
  @Mapping(source = "muscleGroup.description", target = "description")
  MuscleGroup mapMuscleGroups(final MuscleGroupExercise muscleGroupExercise);
}
