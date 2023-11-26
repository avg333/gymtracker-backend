package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdate.infrastructure.model.UpdateWorkoutDateResponse;

public interface UpdateWorkoutDateControllerDocumentation {

  interface Methods {

    @Operation(summary = "Update the date of a Workout")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Workout date updated successfully",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdateWorkoutDateResponse.class))
              }),
          @ApiResponse(
              responseCode = "400",
              description = "Workout for the specified user and date already exists"),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "Workout not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface UpdateWorkoutDateDocumentation {}
  }
}
