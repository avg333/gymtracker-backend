package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model.CopySetGroupsResponseInfrastructure;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface CopySetGroupsController {

  @Operation(
      summary =
          "Copy all the setgroups of the source workout to the destination workout. After that, return all the destination workout setgroups with their sets")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "SetGroups of the destination workout with theirs sets",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetWorkoutResponseInfrastructure.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(
            responseCode = "404",
            description = "Workout source/destination not found",
            content = @Content),
      })
  @PatchMapping("/workouts/{workoutId}/copySetGroups")
  ResponseEntity<List<CopySetGroupsResponseInfrastructure>> execute(
      @PathVariable UUID workoutId,
      @Valid @RequestBody CopySetGroupsRequestInfrastructure copySetGroupsRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException;
}
