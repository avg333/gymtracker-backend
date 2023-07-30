package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model.GetExerciseByIdResponseApplication;
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
class GetExerciseByIdServiceMapperTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks
  private GetExerciseByIdServiceMapper getExerciseByIdServiceMapper =
      Mappers.getMapper(GetExerciseByIdServiceMapper.class);

  @Test
  void testMap() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);

    final GetExerciseByIdResponseApplication result = getExerciseByIdServiceMapper.map(exercise);
    assertThat(result)
        .usingRecursiveComparison()
        .ignoringFields("muscleGroups")
        .isEqualTo(exercise);
    // TODO Finish this test
  }
}
