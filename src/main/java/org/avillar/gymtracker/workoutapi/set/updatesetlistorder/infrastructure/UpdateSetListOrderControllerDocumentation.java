package org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.set.updatesetlistorder.infrastructure.model.UpdateSetListOrderResponse;

public interface UpdateSetListOrderControllerDocumentation {

  interface Methods {

    @Operation(summary = "Update the order of a specific set and reorganize others accordingly")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Sets reordered within the SetGroup",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdateSetListOrderResponse.class))
              }),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "Set not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface UpdateSetListOrderDocumentation {}
  }
}
