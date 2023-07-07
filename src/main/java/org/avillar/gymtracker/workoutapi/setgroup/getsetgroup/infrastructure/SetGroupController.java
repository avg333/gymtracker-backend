package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroup.infrastructure.model.GetSetGroupResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "SetGroups", description = "API to manage setGroups")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface SetGroupController {

  @Operation(summary = "API used to get a setGroup")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "SetGroup by its id",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetSetGroupResponse.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "SetGroup not found", content = @Content)
      })
  @GetMapping("/setGroups/{setGroupId}")
  ResponseEntity<GetSetGroupResponse> execute(@PathVariable UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException;
}
