package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.infrastructure.model.GetAllMuscleGroupsByMuscleSupGroupResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@Tag(name = "MuscleGroups", description = "API to manage MuscleGroups")
@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetAllMuscleGroupsByMuscleSupGroupController {

  /**
   * @deprecated Only for develop
   */
  @Deprecated(forRemoval = true)
  @Operation(summary = "API used to get all the MuscleGroups by MuscleSupGroup", deprecated = true)
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "All the MuscleGroups by MuscleSupGroup",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema =
                      @Schema(implementation = GetAllMuscleGroupsByMuscleSupGroupResponse.class))
            })
      })
  @GetMapping("muscleSupGroups/{muscleSupGroupId}/muscleGroups")
  @ResponseStatus(HttpStatus.OK)
  List<GetAllMuscleGroupsByMuscleSupGroupResponse> execute(@PathVariable UUID muscleSupGroupId);
}
