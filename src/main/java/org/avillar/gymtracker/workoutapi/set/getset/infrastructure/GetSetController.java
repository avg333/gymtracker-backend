package org.avillar.gymtracker.workoutapi.set.getset.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.getset.infrastructure.model.GetSetResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Sets", description = "API to manage sets")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface GetSetController {

  @Operation(summary = "API used to get a set")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Set with the id provided",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetSetResponseInfrastructure.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Set not found", content = @Content)
      })
  @GetMapping("sets/{setId}")
  ResponseEntity<GetSetResponseInfrastructure> execute(@PathVariable UUID setId)
      throws EntityNotFoundException, IllegalAccessException;
}
