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
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetRequestInfrastructure;
import org.avillar.gymtracker.workoutapi.set.createset.infrastructure.model.CreateSetResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
                  schema = @Schema(implementation = CreateSetResponseInfrastructure.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "SetGroup not found", content = @Content)
      })
  @PostMapping("setGroups/{setGroupId}/sets")
  ResponseEntity<CreateSetResponseInfrastructure> execute(
      @PathVariable UUID setGroupId,
      @RequestBody CreateSetRequestInfrastructure createSetRequestInfrastructure)
      throws EntityNotFoundException, IllegalAccessException;
}
