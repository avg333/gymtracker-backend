package org.avillar.gymtracker.authapi.login.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginResponse;

public interface LoginControllerDocumentation {

  interface Methods {

    @Operation(summary = "Authenticate User")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "Login successful",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = LoginResponse.class))
              }),
          @ApiResponse(responseCode = "403", description = "Bad credentials")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface LoginDocumentation {}
  }
}
