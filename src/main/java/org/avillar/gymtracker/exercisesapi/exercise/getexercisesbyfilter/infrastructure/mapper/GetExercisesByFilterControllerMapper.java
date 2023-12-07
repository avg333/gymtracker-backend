package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;
import org.avillar.gymtracker.exercisesapi.common.facade.exercise.GetExercisesFilter;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequest;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.mapstruct.Named;
import org.springframework.util.CollectionUtils;

@Mapper(componentModel = ComponentModel.SPRING)
public interface GetExercisesByFilterControllerMapper {

  List<GetExercisesByFilterResponse> map(List<Exercise> exercises);

  @Mapping(source = "exerciseUses", target = "uses", qualifiedByName = "mapUses")
  GetExercisesByFilterResponse map(Exercise exercise);

  GetExercisesFilter map(GetExercisesByFilterRequest getExercisesByFilterRequest);

  @Named("mapUses")
  default Integer mapUses(final List<ExerciseUses> exerciseUses) {
    if (CollectionUtils.isEmpty(exerciseUses)) {
      return null;
    }

    return exerciseUses.get(0).getUses();
  }
}
