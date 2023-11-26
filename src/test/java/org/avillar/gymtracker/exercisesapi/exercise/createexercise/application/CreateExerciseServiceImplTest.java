package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application;

import org.avillar.gymtracker.exercisesapi.common.adapter.repository.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class CreateExerciseServiceImplTest {

  @InjectMocks private CreateExerciseServiceImpl createExerciseService;

  @Mock private ExerciseDao exerciseDao;
  @Mock private AuthExercisesService authExercisesService;

  @Test
  void shouldCreateExerciseOk() {}

  @Test
  void createNotAccess() {}

  @Test
  void createNotFound() {}
}
