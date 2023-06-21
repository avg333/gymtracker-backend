package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.exercise;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.setgroup.application.update.exercise.UpdateSetGroupExerciseService;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.exercise.model.UpdateSetGroupExerciseRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.exercise.model.UpdateSetGroupExerciseResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateSetGroupExerciseController {

  private final UpdateSetGroupExerciseService updateSetGroupExerciseService;

  @PatchMapping("/setGroups/{setGroupId}/exercise")
  public ResponseEntity<UpdateSetGroupExerciseResponseInfrastructure> updateSetGroupExercise(
      @PathVariable final UUID setGroupId,
      @Valid @RequestBody
          final UpdateSetGroupExerciseRequestInfrastructure
              updateSetGroupExerciseRequestInfrastructure) {
    return ResponseEntity.ok(
        new UpdateSetGroupExerciseResponseInfrastructure(
            updateSetGroupExerciseService.update(
                setGroupId, updateSetGroupExerciseRequestInfrastructure.getExerciseId())));
  }
}
