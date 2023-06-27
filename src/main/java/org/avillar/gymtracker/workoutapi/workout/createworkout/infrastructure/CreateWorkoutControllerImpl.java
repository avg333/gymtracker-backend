package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.DuplicatedWorkoutDateException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.createworkout.application.CreateWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.mapper.CreateWorkoutControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CreateWorkoutControllerImpl implements CreateWorkoutController {

  private final CreateWorkoutService createWorkoutService;
  private final CreateWorkoutControllerMapper createWorkoutControllerMapper;

  @Override
  public ResponseEntity<CreateWorkoutResponseInfrastructure> post(
      final UUID userId,
      final CreateWorkoutRequestInfrastructure createWorkoutRequestInfrastructure)
      throws IllegalAccessException, DuplicatedWorkoutDateException {
    return ResponseEntity.ok(
        createWorkoutControllerMapper.map(
            createWorkoutService.execute(
                userId, createWorkoutControllerMapper.map(createWorkoutRequestInfrastructure))));
  }
}
