package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.infrastructure;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesubgroupsbymusclegroup.infrastructure.model.GetAllMuscleSubGroupByMuscleGroupResponse;

public interface GetAllMuscleSubGroupsByMuscleGroupControllerDocumentation {

  interface Methods {

    @Operation(summary = "Retrieve All Muscle Subgroups by Muscle Groups", deprecated = true)
    @ApiResponses(
        value = {
          @ApiResponse(
              responseCode = "200",
              description = "List of Muscle Subgroups by Muscle Groups",
              content = {
                @Content(
                    mediaType = "application/json",
                    schema =
                        @Schema(implementation = GetAllMuscleSubGroupByMuscleGroupResponse.class))
              })
        })
    @Target(METHOD)
    @Retention(RUNTIME)
    @interface GetAllMuscleSubGroupsByMuscleGroupDocumentation {}
  }
}
