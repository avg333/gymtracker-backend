package org.avillar.gymtracker.usersapi.getusersettings.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.avillar.gymtracker.usersapi.getusersettings.infrastructure.model.GetUserSettingsResponseInfrastructure;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Settings", description = "API to manage Settings")
@RequestMapping(path = "${usersApiPrefix}")
public interface GetUserSettingsController {

  @Operation(summary = "API used to get the user Settings")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User Settings",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetUserSettingsResponseInfrastructure.class))
            })
      })
  @GetMapping("/users/{userId}/settings")
  @ResponseStatus(HttpStatus.OK)
  GetUserSettingsResponseInfrastructure execute(@PathVariable UUID userId);
}
