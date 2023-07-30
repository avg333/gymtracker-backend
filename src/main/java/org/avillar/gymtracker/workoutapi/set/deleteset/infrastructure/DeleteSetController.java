package org.avillar.gymtracker.workoutapi.set.deleteset.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Sets", description = "API to manage sets")
@RequestMapping(path = "${workoutsApiPrefix}/")
public interface DeleteSetController {

  @Operation(summary = "API used to delete a set")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Set deleted"),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Set not found", content = @Content)
      })
  @DeleteMapping("sets/{setId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  Void execute(@PathVariable UUID setId) throws EntityNotFoundException, IllegalAccessException;
}
