package org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.getallmusclesubgroupsbymusclegroup.infrastructure.model.GetAllMuscleSubGroupByMuscleGroupResponseInfrastructure;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "MuscleSubGroups", description = "API to manage MuscleSubGroups")
@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetAllMuscleSubGroupByMuscleGroupController {

  /**
   * @deprecated Only for develop
   */
  @Deprecated(forRemoval = true)
  @Operation(summary = "API used to get all the MuscleSubGroups by MuscleGroup", deprecated = true)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "All the MuscleSubGroups by MuscleGroup",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema =
                      @Schema(
                          implementation =
                              GetAllMuscleSubGroupByMuscleGroupResponseInfrastructure.class))
            })
      })
  @GetMapping("muscleGroups/{muscleGroupId}/muscleSubGroups")
  ResponseEntity<List<GetAllMuscleSubGroupByMuscleGroupResponseInfrastructure>> execute(
      @PathVariable UUID muscleGroupId);
}
