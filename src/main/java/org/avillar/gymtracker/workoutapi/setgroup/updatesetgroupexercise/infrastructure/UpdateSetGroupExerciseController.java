package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.exception.application.ExerciseNotFoundException;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "SetGroups", description = "API to manage setGroups")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateSetGroupExerciseController {

  @Operation(summary = "API used to update the setGroup exerciseId")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "SetGroup exerciseId updated",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UpdateSetGroupExerciseResponse.class))
            }),
        @ApiResponse(
            responseCode = "400",
            description = "The exercise is not valid",
            content = @Content),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "SetGroup not found", content = @Content)
      })
  @PatchMapping("/setGroups/{setGroupId}/exercise")
  ResponseEntity<UpdateSetGroupExerciseResponse> execute(
      @PathVariable UUID setGroupId,
      @Valid @RequestBody UpdateSetGroupExerciseRequest updateSetGroupExerciseRequest)
      throws EntityNotFoundException, IllegalAccessException, ExerciseNotFoundException;
}
