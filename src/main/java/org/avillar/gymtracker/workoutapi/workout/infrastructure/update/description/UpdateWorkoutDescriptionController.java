package org.avillar.gymtracker.workoutapi.workout.infrastructure.update.description;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.update.description.UpdateWorkoutDescriptionService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.description.model.UpdateWorkoutDescriptionRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.description.model.UpdateWorkoutDescriptionResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// RDY
@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateWorkoutDescriptionController {

  private final UpdateWorkoutDescriptionService updateWorkoutDescriptionService;

  @PatchMapping("/workouts/{workoutId}/description")
  public ResponseEntity<UpdateWorkoutDescriptionResponseInfrastructure> updateWorkoutDescription(
      @PathVariable final UUID workoutId,
      @Valid @RequestBody
          final UpdateWorkoutDescriptionRequestInfrastructure
              updateWorkoutDescriptionRequestInfrastructure) {
    return ResponseEntity.ok(
        new UpdateWorkoutDescriptionResponseInfrastructure(
            updateWorkoutDescriptionService.update(
                workoutId, updateWorkoutDescriptionRequestInfrastructure.getDescription())));
  }
}
