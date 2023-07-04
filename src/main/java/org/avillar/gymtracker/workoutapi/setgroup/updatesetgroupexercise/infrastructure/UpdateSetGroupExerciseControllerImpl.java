package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application.UpdateSetGroupExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateSetGroupExerciseControllerImpl implements UpdateSetGroupExerciseController {

  private final UpdateSetGroupExerciseService updateSetGroupExerciseService;

  @Override
  public ResponseEntity<UpdateSetGroupExerciseResponseInfrastructure> execute(
      final UUID setGroupId,
      final UpdateSetGroupExerciseRequestInfrastructure updateSetGroupExerciseRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException, ExerciseNotFoundException {
    return ResponseEntity.ok(
        new UpdateSetGroupExerciseResponseInfrastructure(
            updateSetGroupExerciseService.execute(
                setGroupId, updateSetGroupExerciseRequestInfrastructure.getExerciseId())));
  }
}
