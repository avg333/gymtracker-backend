package org.avillar.gymtracker.authapi.register.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterResponse;

public interface RegisterControllerDocumentation {

  interface Methods {

    @Operation(summary = "Create a New User")
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "User successfully created",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = RegisterResponse.class))
              }),
          @ApiResponse(responseCode = "400", description = "Wrong register code"),
          @ApiResponse(responseCode = "400", description = "Username already exists")
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface RegisterDocumentation {}
  }
}
