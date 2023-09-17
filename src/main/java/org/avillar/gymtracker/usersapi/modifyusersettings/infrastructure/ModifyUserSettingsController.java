package org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.model.ModifyUserSettingsRequestInfrastructure;
import org.avillar.gymtracker.usersapi.modifyusersettings.infrastructure.model.ModifyUserSettingsResponseInfrastructure;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "Settings", description = "API to manage Settings")
@RequestMapping(path = "${usersApiPrefix}")
public interface ModifyUserSettingsController {

  @Operation(summary = "API used to modify the user Settings")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "User Settings modified",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ModifyUserSettingsResponseInfrastructure.class))
            })
      })
  @PostMapping("/users/{userId}/settings")
  @ResponseStatus(HttpStatus.OK)
  ModifyUserSettingsResponseInfrastructure execute(
      @PathVariable UUID userId,
      @Valid @RequestBody ModifyUserSettingsRequestInfrastructure request);
}
