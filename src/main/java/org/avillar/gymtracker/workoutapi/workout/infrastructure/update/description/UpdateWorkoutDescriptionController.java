package org.avillar.gymtracker.workoutapi.workout.infrastructure.update.description;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.update.description.UpdateDescriptionWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.description.model.UpdateWorkoutDescriptionRequest;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.description.model.UpdateWorkoutDescriptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateWorkoutDescriptionController {

  private final UpdateDescriptionWorkoutService updateDescriptionWorkoutService;

  @PatchMapping("/workouts/{workoutId}/description")
  public ResponseEntity<UpdateWorkoutDescriptionResponse> updateWorkoutDescription(
      @PathVariable final UUID workoutId,
      @Valid @RequestBody final UpdateWorkoutDescriptionRequest updateWorkoutDescriptionRequest) {
    return ResponseEntity.ok(
        new UpdateWorkoutDescriptionResponse(
            updateDescriptionWorkoutService.update(
                workoutId, updateWorkoutDescriptionRequest.getDescription())));
  }
}
