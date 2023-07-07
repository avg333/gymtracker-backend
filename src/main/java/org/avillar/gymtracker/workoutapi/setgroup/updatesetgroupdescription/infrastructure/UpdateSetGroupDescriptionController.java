package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure;

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
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionRequest;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription.infrastructure.model.UpdateSetGroupDescriptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "SetGroups", description = "API to manage setGroups")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateSetGroupDescriptionController {

  @Operation(summary = "API used to update the setGroup description")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "SetGroup description updated",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UpdateSetGroupDescriptionResponse.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "SetGroup not found", content = @Content)
      })
  @PatchMapping("/setGroups/{setGroupId}/description")
  ResponseEntity<UpdateSetGroupDescriptionResponse> execute(
      @PathVariable UUID setGroupId,
      @Valid @RequestBody UpdateSetGroupDescriptionRequest updateSetGroupDescriptionRequest)
      throws EntityNotFoundException, IllegalAccessException;
}
