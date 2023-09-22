package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterRequest;
import org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyfilter.infrastructure.model.GetExercisesByFilterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

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
            }) // TODO Explain requestParams
      })
  @GetMapping("exercises/filter")
  @ResponseStatus(HttpStatus.OK)
  List<GetExercisesByFilterResponse> execute(
      GetExercisesByFilterRequest getExercisesByFilterRequest);
}
