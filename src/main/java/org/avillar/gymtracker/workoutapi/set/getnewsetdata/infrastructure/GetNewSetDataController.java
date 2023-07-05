package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model.GetNewSetDataResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Sets", description = "API to manage sets")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetNewSetDataController {

  @Operation(summary = "API used to get the data for a new set")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "New set data",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetNewSetDataResponseInfrastructure.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "SetGroup not found", content = @Content)
      })
  @GetMapping("setGroups/{setGroupId}/sets/newSet")
  ResponseEntity<GetNewSetDataResponseInfrastructure> execute(@PathVariable UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException;
}
