package org.avillar.gymtracker.authapi.login.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginRequest;
import org.avillar.gymtracker.authapi.login.infrastructure.model.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Login", description = "API to manage Login")
@RequestMapping(path = "${authApiPrefix}")
public interface LoginController {

  @Operation(summary = "API used to login")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Login successful",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = LoginResponse.class))
            })
      })
  @PostMapping("${authApiEndpoint}")
  @ResponseStatus(HttpStatus.OK)
  LoginResponse execute(@Valid @RequestBody LoginRequest loginRequest);
}
