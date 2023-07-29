package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.mapper;

import java.util.ArrayList;
import java.util.List;
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

  @AfterMapping // TODO TEST THIS
  default void mapMuscleGroups(
      @MappingTarget final GetExerciseByIdResponseApplication getExerciseByIdResponseApplication,
      final Exercise exercise) {
    if (getExerciseByIdResponseApplication == null
        || exercise == null
        || CollectionUtils.isEmpty(exercise.getMuscleGroupExercises())) {
      return;
    }

    final List<MuscleGroup> muscleGroups = new ArrayList<>();

    for (final MuscleGroupExercise muscleGroupExercise : exercise.getMuscleGroupExercises()) {
      final MuscleGroup muscleGroup = new MuscleGroup();
      muscleGroup.setWeight(muscleGroupExercise.getWeight());
      muscleGroup.setId(muscleGroupExercise.getMuscleGroup().getId());
      muscleGroup.setName(muscleGroupExercise.getMuscleGroup().getName());
      muscleGroup.setDescription(muscleGroupExercise.getMuscleGroup().getDescription());

      muscleGroups.add(muscleGroup);
    }

    getExerciseByIdResponseApplication.setMuscleGroups(muscleGroups);
  }
}
