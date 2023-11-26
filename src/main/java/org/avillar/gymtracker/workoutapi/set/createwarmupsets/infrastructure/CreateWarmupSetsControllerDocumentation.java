package org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.set.createwarmupsets.infrastructure.model.CreateWarmupSetsResponse;

public interface CreateWarmupSetsControllerDocumentation {

  interface Methods {

    @Operation(summary = "Create and retrieve warmup Sets for a SetGroup")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Warmup sets successfully created",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CreateWarmupSetsResponse.class))
              }),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "SetGroup not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface CreateWarmupSetsDocumentation {}
  }
}
