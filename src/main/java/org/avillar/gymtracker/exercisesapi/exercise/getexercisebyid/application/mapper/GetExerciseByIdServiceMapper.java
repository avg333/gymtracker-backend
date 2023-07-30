package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.mapper;

import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication.MuscleGroup;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.util.CollectionUtils;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GetExerciseByIdServiceMapper {

  GetExerciseByIdResponseApplication map(Exercise exercise);

  @AfterMapping
  default void mapMuscleGroups(
      @MappingTarget final GetExerciseByIdResponseApplication getExerciseByIdResponseApplication,
      final Exercise exercise) {
    if (getExerciseByIdResponseApplication == null
        || exercise == null
        || CollectionUtils.isEmpty(exercise.getMuscleGroupExercises())) {
      return;
    }

    getExerciseByIdResponseApplication.setMuscleGroups(
        exercise.getMuscleGroupExercises().stream().map(this::createMuscleGroup).toList());
  }

  private MuscleGroup createMuscleGroup(final MuscleGroupExercise muscleGroupExercise) {
    final MuscleGroup muscleGroup = new MuscleGroup();
    muscleGroup.setWeight(muscleGroupExercise.getWeight());
    muscleGroup.setId(muscleGroupExercise.getMuscleGroup().getId());
    muscleGroup.setName(muscleGroupExercise.getMuscleGroup().getName());
    muscleGroup.setDescription(muscleGroupExercise.getMuscleGroup().getDescription());
    return muscleGroup;
  }
}
