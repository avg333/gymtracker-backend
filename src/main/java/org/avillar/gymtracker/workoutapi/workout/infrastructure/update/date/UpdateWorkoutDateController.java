package org.avillar.gymtracker.workoutapi.workout.infrastructure.update.date;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.update.date.UpdateWorkoutDateService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.date.model.UpdateWorkoutDateRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.date.model.UpdateWorkoutDateResponseInfrastructure;
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
public class UpdateWorkoutDateController {

  private final UpdateWorkoutDateService updateWorkoutDateService;

  @PatchMapping("/workouts/{workoutId}/date")
  public ResponseEntity<UpdateWorkoutDateResponseInfrastructure> updateWorkoutDate(
      @PathVariable final UUID workoutId,
      @Valid @RequestBody
          final UpdateWorkoutDateRequestInfrastructure updateWorkoutDateRequestInfrastructure) {
    return ResponseEntity.ok(
        new UpdateWorkoutDateResponseInfrastructure(
            updateWorkoutDateService.update(
                workoutId, updateWorkoutDateRequestInfrastructure.getDate())));
  }
}
