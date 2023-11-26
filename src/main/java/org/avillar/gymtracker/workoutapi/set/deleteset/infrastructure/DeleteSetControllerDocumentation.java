package org.avillar.gymtracker.workoutapi.set.deleteset.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

public interface DeleteSetControllerDocumentation {

  interface Methods {

    @Operation(summary = "Delete a Set")
    @ApiResponses(
        value = {
          @ApiResponse(responseCode = "204", description = "Set successfully deleted"),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "Set not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface DeleteSetDocumentation {}
  }
}
