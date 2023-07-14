package org.avillar.gymtracker.authapi.register.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterRequest;
import org.avillar.gymtracker.authapi.register.infrastructure.model.RegisterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Register", description = "API to manage Register")
@RequestMapping(path = "${authApiPrefix}")
public interface RegisterController {

  @Operation(summary = "API used to create a new user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = RegisterResponse.class))
            })
      })
  @PostMapping("${authApiRegisterEndpoint}")
  ResponseEntity<RegisterResponse> execute(@Valid @RequestBody RegisterRequest registerRequest);
}
