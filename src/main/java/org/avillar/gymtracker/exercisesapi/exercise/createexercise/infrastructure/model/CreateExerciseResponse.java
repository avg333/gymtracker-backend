package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateExerciseResponse {

  @Schema(description = "Exercise id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
  private UUID id;

  @Schema(description = "Exercise name", example = "bench press")
  private String name;

  @Schema(description = "Exercise description", example = "barbell flat press")
  private String description;

  @Schema(description = "Exercise is executed unilaterally", example = "false")
  private boolean unilateral;
}
