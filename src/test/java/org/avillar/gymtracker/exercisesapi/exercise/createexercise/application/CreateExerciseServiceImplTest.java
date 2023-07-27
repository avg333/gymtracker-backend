package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application;

import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.mapper.CreateExerciseApplicationMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateExerciseServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks CreateExerciseServiceImpl createExerciseService;

  @Mock private ExerciseDao exerciseDao;
  @Mock private AuthExercisesService authExercisesService;

  @Spy
  private CreateExerciseApplicationMapper createExerciseApplicationMapper =
      Mappers.getMapper(CreateExerciseApplicationMapper.class);

  @Test
  void createOk() {}

  @Test
  void createNotAccess() {}

  @Test
  void createNotFound() {}
}
