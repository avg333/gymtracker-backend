package org.avillar.gymtracker.usersapi.modifysettings.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.usersapi.modifysettings.infrastructure.model.ModifySettingsResponseDto;

public interface ModifySettingsControllerDocumentation {

  interface Methods {

    @Operation(summary = "Modify User Settings")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "User settings successfully updated",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ModifySettingsResponseDto.class))
              }),
          @ApiResponse(responseCode = "403", description = "Unauthorized access")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface ModifySettingsDocumentation {}
  }
}
