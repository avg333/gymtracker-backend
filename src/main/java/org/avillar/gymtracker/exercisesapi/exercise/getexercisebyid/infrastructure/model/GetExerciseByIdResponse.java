package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetExerciseByIdResponse {

  @Schema(description = "Exercise id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
  private UUID id;

  @Schema(description = "Exercise name", example = "bench press")
  private String name;

  @Schema(description = "Exercise description", example = "barbell flat press")
  private String description;

  @Schema(description = "Exercise is executed unilaterally", example = "false")
  private boolean unilateral;

  @Schema(description = "Exercise LoadType")
  private LoadType loadType;

  @Schema(description = "Exercise MuscleSubGroups")
  private List<MuscleSubGroup> muscleSubGroups;

  @Schema(description = "Exercise MuscleGroups with weight coefficient")
  private List<MuscleGroup> muscleGroups;

  @Data
  public static class LoadType {

    @Schema(description = "LoadType id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
    private UUID id;

    @Schema(description = "LoadType name", example = "bar")
    private String name;

    @Schema(
        description = "LoadType description",
        example = "Weight guide machine used for weight training")
    private String description;
  }

  @Data
  public static class MuscleSubGroup {

    @Schema(description = "MuscleSubGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
    private UUID id;

    @Schema(description = "MuscleSubGroup name", example = "Lower chest")
    private String name;

    @Schema(description = "MuscleSubGroup name", example = "Lower part of the pectoral")
    private String description;
  }

  @Data
  public static class MuscleGroup {

    @Schema(description = "MuscleGroup id", example = "5819d9ab-5251-4916-9b5e-6cd36a01d560")
    private UUID id;

    @Schema(description = "Exercise name", example = "bench press")
    private String name;

    @Schema(description = "Exercise description", example = "barbell flat press")
    private String description;

    @Schema(description = "MuscleGroup coefficient weight", example = "0.7")
    private Double weight;
  }
}
