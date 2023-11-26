package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseDto;

public interface GetWorkoutDetailsControllerDocumentation {

  interface Methods {

    @Operation(summary = "Retrieve a Workout by its ID with all the SetGroups and Sets")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Workout data with SetGroups and Sets retrieved successfully",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetWorkoutDetailsResponseDto.class))
              }),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "Workout not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface GetWorkoutDetailsDocumentation {}
  }
}
