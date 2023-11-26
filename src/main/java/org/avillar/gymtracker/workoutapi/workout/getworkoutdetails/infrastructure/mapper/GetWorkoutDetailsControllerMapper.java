package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.MuscleGroupExercise;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetWorkoutDetailsControllerMapper {

  GetWorkoutDetailsResponseDto map(Workout workout);

  GetWorkoutDetailsResponseDto.SetGroup map(SetGroup setGroup);

  @Mapping(
      source = "muscleGroupExercises",
      target = "muscleGroups",
      qualifiedByName = "mapMuscleGroups")
  GetWorkoutDetailsResponseDto.SetGroup.Exercise map(Exercise exercise);

  @Named("mapMuscleGroups")
  default List<GetWorkoutDetailsResponseDto.SetGroup.Exercise.MuscleGroup> map(
      List<MuscleGroupExercise> muscleGroupExercises) {
    if (muscleGroupExercises == null) {
      return null;
    }

    return muscleGroupExercises.stream().map(this::map).toList();
  }

  default GetWorkoutDetailsResponseDto.SetGroup.Exercise.MuscleGroup map(
      MuscleGroupExercise muscleGroupExercise) {
    if (muscleGroupExercise == null || muscleGroupExercise.getMuscleGroup() == null) {
      return null;
    }

    return new GetWorkoutDetailsResponseDto.SetGroup.Exercise.MuscleGroup(
        muscleGroupExercise.getMuscleGroup().getId(),
        muscleGroupExercise.getMuscleGroup().getName(),
        muscleGroupExercise.getMuscleGroup().getDescription(),
        muscleGroupExercise.getWeight());
  }
}
