package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface DeleteExerciseControllerDocumentation {

  interface Methods {

    @Operation(summary = "Delete an Exercise")
    @ApiResponses(
        value = {
          @ApiResponse(responseCode = "204", description = "Exercise Successfully Deleted"),
          @ApiResponse(responseCode = "403", description = "Authorization Denied"),
          @ApiResponse(responseCode = "404", description = "Exercise Not Found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface DeleteExerciseDocumentation {}
  }
}
