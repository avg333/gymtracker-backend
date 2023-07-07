package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.application.UpdateSetGroupExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UpdateSetGroupExerciseControllerImpl implements UpdateSetGroupExerciseController {

  private final UpdateSetGroupExerciseService updateSetGroupExerciseService;

  @Override
  public ResponseEntity<UpdateSetGroupExerciseResponse> execute(
      final UUID setGroupId, final UpdateSetGroupExerciseRequest updateSetGroupExerciseRequest)
      throws EntityNotFoundException, IllegalAccessException, ExerciseNotFoundException {
    return ResponseEntity.ok(
        new UpdateSetGroupExerciseResponse(
            updateSetGroupExerciseService.execute(
                setGroupId, updateSetGroupExerciseRequest.getExerciseId())));
  }
}
