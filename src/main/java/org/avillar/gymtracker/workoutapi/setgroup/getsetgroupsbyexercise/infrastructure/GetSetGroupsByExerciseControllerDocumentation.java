package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model.GetSetGroupsByExerciseResponse;

public interface GetSetGroupsByExerciseControllerDocumentation {

  interface Methods {

    @Operation(summary = "Retrieve user SetGroups with the exerciseId provided")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "SetGroups retrieved successfully",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetSetGroupsByExerciseResponse.class))
              }),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface GetSetGroupsByExerciseDocumentation {}
  }
}
