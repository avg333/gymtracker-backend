package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Exercises", description = "API to manage Exercises")
@RequestMapping(path = "${exercisesApiPrefix}")
public interface GetExercisesByFilterController {

  @Operation(summary = "API used to get the exercises filtered")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Exercises filtered",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = GetExercisesByFilterResponse.class))
            }) // TODO Explicar requertParams
      })
  @GetMapping("exercises/filter")
  ResponseEntity<List<GetExercisesByFilterResponse>> execute(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String description,
      @RequestParam(required = false) Boolean unilateral,
      @RequestParam(required = false) List<UUID> loadTypeIds,
      @RequestParam(required = false) List<UUID> muscleSupGroupIds,
      @RequestParam(required = false) List<UUID> muscleGroupId,
      @RequestParam(required = false) List<UUID> muscleSubGroupIds);
}
