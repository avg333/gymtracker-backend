package org.avillar.gymtracker.exercisesapi.exercise.createexercise.infrastructure.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateExerciseRequest {

  private String name;

  private String description;

  private Boolean unilateral;

  private UUID loadTypeId;

  private List<UUID> muscleSubGroupsIds;

  private List<MuscleGroupExercises> muscleGroupExercises = new ArrayList<>();

  @Data
  public static class MuscleGroupExercises {
    private UUID muscleGroupId;

    private double weight;
  }
}
