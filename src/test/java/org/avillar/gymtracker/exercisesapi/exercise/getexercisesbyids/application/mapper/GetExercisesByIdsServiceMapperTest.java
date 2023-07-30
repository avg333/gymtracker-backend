package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model.GetExercisesByIdsResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetExercisesByIdsServiceMapperTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks
  private GetExercisesByIdsServiceMapper getExercisesByIdsServiceMapper =
      Mappers.getMapper(GetExercisesByIdsServiceMapper.class);

  @Test
  void testMap() {
    final List<Exercise> exercises = easyRandom.objects(Exercise.class, LIST_SIZE).toList();

    final List<GetExercisesByIdsResponseApplication> result =
        getExercisesByIdsServiceMapper.map(exercises);
    assertThat(result)
        .usingRecursiveComparison()
        .ignoringFields("muscleGroups")
        .isEqualTo(exercises);
    // TODO Finish this test
  }
}
