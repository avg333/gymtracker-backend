package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.setgroup.createsetgroup.infrastructure.model.CreateSetGroupResponse;

public interface CreateSetGroupControllerDocumentation {

  interface Methods {

    @Operation(summary = "Create a new SetGroup")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "SetGroup successfully created",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CreateSetGroupResponse.class))
              }),
          @ApiResponse(responseCode = "400", description = "Invalid exercise data provided"),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "Workout not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface CreateSetGroupDocumentation {}
  }
}
