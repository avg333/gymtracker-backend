package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription.infrastructure.model.UpdateWorkoutDescriptionResponse;

public interface UpdateWorkoutDescriptionControllerDocumentation {

  interface Methods {

    @Operation(summary = "Update the description of a Workout")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Workout description updated successfully",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdateWorkoutDescriptionResponse.class))
              }),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "Workout not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface UpdateWorkoutDescriptionDocumentation {}
  }
}
