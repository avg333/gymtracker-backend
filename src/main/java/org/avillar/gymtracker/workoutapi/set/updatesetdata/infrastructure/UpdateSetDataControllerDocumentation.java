package org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.workoutapi.set.updatesetdata.infrastructure.model.UpdateSetDataResponseDto;

public interface UpdateSetDataControllerDocumentation {

  interface Methods {

    @Operation(summary = "Update the data of a set")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Set data successfully updated",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UpdateSetDataResponseDto.class))
              }),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "Set not found")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface UpdateSetDataDocumentation {}
  }
}
