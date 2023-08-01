package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class CreateExerciseRequestApplication {

  private String name;

  private String description;

  private Boolean unilateral;

  private UUID loadTypeId;

  private List<UUID> muscleSubGroupsIds;

  private List<MuscleGroupExercises> muscleGroups = new ArrayList<>();

  @Data
  public static class MuscleGroupExercises {
    
    private UUID muscleGroupId;

    private Double weight;
  }
}
