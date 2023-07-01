package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(path = "${workoutsApiPrefix}/")
public interface DeleteSetGroupController {

  @Operation(summary = "Delete a setGroup by its id")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Deleted the setGroup"),
        @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content),
        @ApiResponse(responseCode = "404", description = "Workout not found", content = @Content)
      })
  @DeleteMapping("/setGroups/{setGroupId}")
  ResponseEntity<Void> execute(@PathVariable UUID setGroupId)
      throws EntityNotFoundException, IllegalAccessException;
}
