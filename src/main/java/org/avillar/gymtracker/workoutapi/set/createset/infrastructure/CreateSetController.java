package org.avillar.gymtracker.workoutapi.set.createset.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequest;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Sets", description = "API to manage sets")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface CreateSetController {

  @Operation(summary = "API used to create a set")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Set created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateSetResponse.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "SetGroup not found", content = @Content)
      })
  @PostMapping("setGroups/{setGroupId}/sets")
  @ResponseStatus(HttpStatus.OK)
  CreateSetResponse execute(
      @PathVariable UUID setGroupId, @RequestBody CreateSetRequest createSetRequest)
      throws EntityNotFoundException, IllegalAccessException;
}
