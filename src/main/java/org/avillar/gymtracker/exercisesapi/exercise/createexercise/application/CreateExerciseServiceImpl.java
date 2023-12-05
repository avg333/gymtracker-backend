package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateExerciseServiceImpl implements CreateExerciseService {

  @Override
  public Exercise execute(final UUID userId, final Exercise exercise)
      throws EntityNotFoundException, IllegalAccessException {
    throw new NotImplementedException();
  }
}
