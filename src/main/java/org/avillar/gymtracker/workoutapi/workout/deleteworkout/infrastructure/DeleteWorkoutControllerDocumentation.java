package org.avillar.gymtracker.workoutapi.workout.deleteworkout.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface DeleteWorkoutControllerDocumentation {

  interface Methods {

    @Operation(summary = "Delete a Workout")
    @ApiResponses(
        value = {
          @ApiResponse(responseCode = "204", description = "Workout successfully deleted"),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "Workout not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface DeleteWorkoutDocumentation {}
  }
}
