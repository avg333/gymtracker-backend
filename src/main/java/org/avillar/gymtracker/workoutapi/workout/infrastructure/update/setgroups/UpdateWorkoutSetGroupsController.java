package org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups;

import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.workout.application.update.setgroups.UpdateWorkoutSetGroupsService;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups.mapper.UpdateWorkoutSetGroupsControllerMapper;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups.model.UpdateWorkoutSetGroupsRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.infrastructure.update.setgroups.model.UpdateWorkoutSetGroupsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// FINALIZAR
@RestController
@RequestMapping(path = "${workoutsApiPrefix}/")
@RequiredArgsConstructor
public class UpdateWorkoutSetGroupsController {

  private final UpdateWorkoutSetGroupsService updateWorkoutSetGroupsService;
  private final UpdateWorkoutSetGroupsControllerMapper updateWorkoutSetGroupsControllerMapper;

  @PatchMapping("/workouts/{workoutId}/setGroups")
  public ResponseEntity<UpdateWorkoutSetGroupsResponseInfrastructure> updateWorkoutSetGroups(
      @PathVariable final UUID workoutId,
      @Valid @RequestBody
          final UpdateWorkoutSetGroupsRequestInfrastructure
              updateWorkoutSetGroupsRequestInfrastructure) {
    return ResponseEntity.ok(
        updateWorkoutSetGroupsControllerMapper.updateResponse(
            updateWorkoutSetGroupsRequestInfrastructure.getSource()
                    == UpdateWorkoutSetGroupsRequestInfrastructure.Source.WORKOUT
                ? updateWorkoutSetGroupsService.addSetGroupsToWorkoutFromWorkout(
                    workoutId, updateWorkoutSetGroupsRequestInfrastructure.getId())
                : updateWorkoutSetGroupsService.addSetGroupsToWorkoutFromSession(
                    workoutId, updateWorkoutSetGroupsRequestInfrastructure.getId())));
  }
}
