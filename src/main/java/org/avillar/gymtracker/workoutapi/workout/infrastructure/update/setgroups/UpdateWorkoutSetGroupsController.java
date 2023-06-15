package org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.update.setgroups.UpdateWorkoutSetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups.mapper.UpdateWorkoutSetGroupsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups.model.UpdateWorkoutSetGroupsRequest;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups.model.UpdateWorkoutSetGroupsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateWorkoutSetGroupsController {

  private final UpdateWorkoutSetGroupsService updateWorkoutSetGroupsService;
  private final UpdateWorkoutSetGroupsControllerMapper updateWorkoutSetGroupsControllerMapper;

  @PatchMapping("/workouts/{workoutId}/setGroups")
  public ResponseEntity<UpdateWorkoutSetGroupsResponse> updateWorkoutSetGroups(
      @PathVariable final UUID workoutId,
      @Valid @RequestBody final UpdateWorkoutSetGroupsRequest updateWorkoutSetGroupsRequest) {
    return ResponseEntity.ok(
        updateWorkoutSetGroupsControllerMapper.updateResponse(
            updateWorkoutSetGroupsRequest.getSource()
                    == UpdateWorkoutSetGroupsRequest.Source.WORKOUT
                ? updateWorkoutSetGroupsService.addSetGroupsToWorkoutFromWorkout(
                    workoutId, updateWorkoutSetGroupsRequest.getId())
                : updateWorkoutSetGroupsService.addSetGroupsToWorkoutFromSession(
                    workoutId, updateWorkoutSetGroupsRequest.getId())));
  }
}
