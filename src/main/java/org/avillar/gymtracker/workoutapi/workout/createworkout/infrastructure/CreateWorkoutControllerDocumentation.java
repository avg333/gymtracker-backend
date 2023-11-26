package org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponse;

public interface CreateWorkoutControllerDocumentation {

  interface Methods {

    @Operation(summary = "Create a new Workout")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Workout successfully created",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CreateWorkoutResponse.class))
              }),
          @ApiResponse(
              responseCode = "400",
              description = "Workout for the specified user and date already exists"),
          @ApiResponse(responseCode = "403", description = "Unauthorized access")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface CreateWorkoutDocumentation {}
  }
}
