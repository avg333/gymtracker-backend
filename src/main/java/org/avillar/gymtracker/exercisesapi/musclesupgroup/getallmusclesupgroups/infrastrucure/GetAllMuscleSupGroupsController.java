package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.model.GetAllMuscleSupGroupsResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "MuscleSupGroups", description = "API to manage MuscleSupGroups")
@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetAllMuscleSupGroupsController {

  @Operation(summary = "API used to get all the MuscleSupGroups")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "All the MuscleSupGroups",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema =
                      @Schema(implementation = GetAllMuscleSupGroupsResponseInfrastructure.class))
            })
      })
  @GetMapping("muscleSupGroups")
  ResponseEntity<List<GetAllMuscleSupGroupsResponseInfrastructure>> execute();
}
