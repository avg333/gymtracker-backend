package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid.infrastructure.model.GetWorkoutsDateAndIdResponse;

public interface GetWorkoutsDateAndIdControllerDocumentation {

  interface Methods {

    @Operation(summary = "Retrieve all user workouts dates and ids")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Workout dates and ids retrieved successfully",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetWorkoutsDateAndIdResponse.class))
              }),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface GetWorkoutsDateAndIdDocumentation {}
  }
}
