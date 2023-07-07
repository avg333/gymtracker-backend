package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.ExerciseNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "SetGroups", description = "API to manage setGroups")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface CreateSetGroupController {

  @Operation(summary = "API used to create a setGroup")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "SetGroup created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateSetGroupResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "The exercise is not valid",
            content = @Content),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content)
      })
  @PostMapping("/workouts/{workoutId}/setGroups")
  ResponseEntity<CreateSetGroupResponse> execute(
      @PathVariable UUID workoutId, @Valid @RequestBody CreateSetGroupRequest createSetGroupRequest)
      throws EntityNotFoundException, IllegalAccessException, ExerciseNotFoundException;
}
