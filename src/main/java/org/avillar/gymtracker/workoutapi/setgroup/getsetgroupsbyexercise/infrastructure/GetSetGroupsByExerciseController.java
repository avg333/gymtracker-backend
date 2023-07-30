package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetSetGroupsByExerciseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "SetGroups", description = "API to manage setGroups")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetSetGroupsByExerciseController {

  @Operation(summary = "API used to get the user setGroups with the provided exercise")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "SetGroups from the user provided with the exercise provided",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetSetGroupsByExerciseResponse.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
      })
  @GetMapping("/users/{userId}/exercises/{exerciseId}/setGroups")
  @ResponseStatus(HttpStatus.OK)
  List<GetSetGroupsByExerciseResponse> execute(
      @PathVariable UUID userId, @PathVariable UUID exerciseId) throws IllegalAccessException;
}
