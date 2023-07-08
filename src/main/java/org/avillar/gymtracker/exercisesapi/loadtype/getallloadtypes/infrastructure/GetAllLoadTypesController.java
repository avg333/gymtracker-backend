package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "LoadTypes", description = "API to manage LoadTypes")
@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetAllLoadTypesController {

  @Operation(summary = "API used to get all the LoadTypes")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "All the LoadTypes",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetAllLoadTypesResponse.class))
            })
      })
  @GetMapping("/loadTypes")
  ResponseEntity<List<GetAllLoadTypesResponse>> execute();
}
