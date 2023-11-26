package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model.CreateExerciseResponse;

public interface CreateExerciseControllerDocumentation {

  interface Methods {

    @Operation(summary = "Create an Exercise")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Exercise Successfully Created",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CreateExerciseResponse.class))
              }),
          @ApiResponse(responseCode = "403", description = "Authorization Denied"),
          @ApiResponse(responseCode = "404", description = "SubEntity Not Found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface CreateDocumentation {}
  }
}
