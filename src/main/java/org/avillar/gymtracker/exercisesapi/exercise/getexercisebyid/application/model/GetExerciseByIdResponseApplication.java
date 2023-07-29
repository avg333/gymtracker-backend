package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid.application.model;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetExerciseByIdResponseApplication {

  private UUID id;

  private String name;

  private String description;

  private boolean unilateral;

  private LoadType loadType;

  private List<MuscleSubGroup> muscleSubGroups;

  private List<MuscleGroup> muscleGroups;

  @Data
  public static class LoadType {

    private UUID id;

    private String name;

    private String description;
  }

  @Data
  public static class MuscleSubGroup {

    private UUID id;
    
    private String name;
    
    private String description;
  }

  @Data
  public static class MuscleGroup {

    private UUID id;

    private String name;

    private String description;

    private Double weight;
  }
}
