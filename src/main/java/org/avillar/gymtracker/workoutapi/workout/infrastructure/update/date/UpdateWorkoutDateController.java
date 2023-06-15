package org.avillar.gymtracker.workoutapi.workout.infrastructure.update.date;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.update.date.UpdateDateWorkoutService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.date.model.UpdateWorkoutDateRequest;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.date.model.UpdateWorkoutDateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateWorkoutDateController {

  private final UpdateDateWorkoutService updateDateWorkoutService;

  @PatchMapping("/workouts/{workoutId}/date")
  public ResponseEntity<UpdateWorkoutDateResponse> updateWorkoutDate(
      @PathVariable final UUID workoutId,
      @Valid @RequestBody final UpdateWorkoutDateRequest updateWorkoutDateRequest) {
    return ResponseEntity.ok(
        new UpdateWorkoutDateResponse(
            updateDateWorkoutService.update(workoutId, updateWorkoutDateRequest.getDate())));
  }
}
