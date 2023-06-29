package org.avillar.gymtracker.workoutapi.exercise.application.model;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetExerciseResponseFacade {
  private UUID id;
  private String name;
  private String description;
  private LoadType loadType;
  private Set<MuscleSubGroup> muscleSubGroups;
  private Set<MuscleGroupExercise> muscleGroupExercises;

  @Data
  @AllArgsConstructor
  public static class LoadType {
    private UUID id;
    private String name;
    private String description;
  }

  @Data
  @AllArgsConstructor
  public static class MuscleSubGroup {
    private UUID id;
    private String name;
    private String description;
  }

  @Data
  @AllArgsConstructor
  public static class MuscleGroupExercise {
    private UUID id;
    private Double weight;
    private MuscleGroup muscleGroup;
  }

  @Data
  @AllArgsConstructor
  public static class MuscleGroup {
    private UUID id;
    private String name;
    private String description;
    private Set<MuscleSupGroup> muscleSupGroups;
  }

  @Data
  @AllArgsConstructor
  public static class MuscleSupGroup {
    private UUID id;
    private String name;
    private String description;
  }
}
