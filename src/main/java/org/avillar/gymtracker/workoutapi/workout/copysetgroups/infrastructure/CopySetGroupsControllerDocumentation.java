package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.workout.getworkout.infrastructure.model.GetWorkoutResponse;

public interface CopySetGroupsControllerDocumentation {

  interface Methods {

    @Operation(
        summary =
            "Copy all the setgroups of the source workout to the destination workout. After that, return all the destination workout setgroups with their sets")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "SetGroups copied successfully",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetWorkoutResponse.class))
              }),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "Workout source/destination not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface CopySetGroupsDocumentation {}
  }
}
