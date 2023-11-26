package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponse;

public interface GetAllLoadTypesControllerDocumentation {

  interface Methods {

    @Operation(summary = "Retrieve All Load Types")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "List of All Load Types",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetAllLoadTypesResponse.class))
              })
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface GetAllLoadTypesDocumentation {}
  }
}
