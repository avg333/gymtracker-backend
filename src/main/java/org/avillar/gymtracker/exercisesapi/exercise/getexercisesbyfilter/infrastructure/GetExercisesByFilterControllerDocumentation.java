package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;

public interface GetExercisesByFilterControllerDocumentation {

  interface Methods {

    @Operation(summary = "API used to get the exercises filtered")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Exercises filtered",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetExercisesByFilterResponse.class))
              }) // TODO Explain requestParams
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface GetExercisesByFilterDocumentation {}
  }
}
