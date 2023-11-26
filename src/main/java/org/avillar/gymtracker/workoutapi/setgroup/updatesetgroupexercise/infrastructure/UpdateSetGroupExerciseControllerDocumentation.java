package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupexercise.infrastructure.model.UpdateSetGroupExerciseResponse;

public interface UpdateSetGroupExerciseControllerDocumentation {

  interface Methods {

    @Operation(summary = "Update the exerciseId of a SetGroup")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "SetGroup exerciseId updated successfully",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdateSetGroupExerciseResponse.class))
              }),
          @ApiResponse(responseCode = "400", description = "Invalid exerciseId provided"),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "SetGroup not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface UpdateSetGroupExerciseDocumentation {}
  }
}
