package org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.set.getnewsetdata.infrastructure.model.GetNewSetDataResponse;

public interface GetNewSetDataControllerDocumentation {

  interface Methods {

    @Operation(summary = "Retrieve Set data information for a new Set in a SetGroup")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "New set data retrieved successfully",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetNewSetDataResponse.class))
              }),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "SetGroup not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface GetNewSetDataDocumentation {}
  }
}
