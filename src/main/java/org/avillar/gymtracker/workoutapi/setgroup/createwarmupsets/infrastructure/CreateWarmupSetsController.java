package org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.model.CreateWarmupSetsRequest;
import org.avillar.gymtracker.workoutapi.setgroup.createwarmupsets.infrastructure.model.CreateWarmupSetsResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "SetGroups", description = "API to manage setGroups")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface CreateWarmupSetsController {

  @Operation(summary = "API used to create warmup sets for a setGroup")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Warmup sets created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CreateWarmupSetsResponse.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "SetGroup not found", content = @Content)
      })
  @PostMapping("/setGroups/{setGroupId}/setGroups/warmup")
  @ResponseStatus(HttpStatus.OK)
  CreateWarmupSetsResponse execute(
      @PathVariable UUID setGroupId, CreateWarmupSetsRequest createWarmupSetsRequest);
}
