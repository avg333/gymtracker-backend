package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponse;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponse.MuscleGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetExercisesByIdsControllerMapper {

  List<GetExercisesByIdsResponse> map(List<Exercise> exercises);

  @Mapping(
      source = "muscleGroupExercises",
      target = "muscleGroups",
      qualifiedByName = "mapMuscleGroups")
  GetExercisesByIdsResponse map(Exercise exercise);

  @Named("mapMuscleGroups")
  @Mapping(source = "muscleGroup.name", target = "name")
  @Mapping(source = "muscleGroup.description", target = "description")
  MuscleGroup mapMuscleGroups(final MuscleGroupExercise muscleGroupExercise);
}
