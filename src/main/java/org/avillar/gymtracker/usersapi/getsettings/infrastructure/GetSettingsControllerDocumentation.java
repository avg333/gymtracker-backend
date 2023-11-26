package org.avillar.gymtracker.usersapi.getsettings.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.usersapi.getsettings.infrastructure.model.GetSettingsResponse;

public interface GetSettingsControllerDocumentation {

  interface Methods {

    @Operation(summary = "Get User Settings")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Successfully retrieve user settings",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = GetSettingsResponse.class))
              }),
          @ApiResponse(responseCode = "403", description = "Unauthorized access"),
          @ApiResponse(responseCode = "404", description = "User has no settings")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface GetSettingsDocumentation {}
  }
}
