package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.infrastructure.model.GetExercisesByIdsResponse;

public interface GetExercisesByIdsControllerDocumentation {

  interface Methods {

    @Operation(summary = "API used to get exercises by their ids")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Exercises with the ids provided",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetExercisesByIdsResponse.class))
              }),
          @ApiResponse(responseCode = "403", description = "Not authorized", content = @Content)
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface GetExercisesByIdsDocumentation {}
  }
}
