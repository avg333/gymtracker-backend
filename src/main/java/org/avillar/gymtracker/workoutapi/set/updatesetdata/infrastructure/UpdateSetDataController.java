package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataRequest;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Sets", description = "API to manage sets")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface UpdateSetDataController {

  @Operation(summary = "API used to update the set data")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Set data updated",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UpdateSetDataResponse.class))
            }),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Set not found", content = @Content)
      })
  @PatchMapping("sets/{setId}")
  ResponseEntity<UpdateSetDataResponse> execute(
      @PathVariable UUID setId, @RequestBody UpdateSetDataRequest updateSetDataRequest)
      throws EntityNotFoundException, IllegalAccessException;
}
