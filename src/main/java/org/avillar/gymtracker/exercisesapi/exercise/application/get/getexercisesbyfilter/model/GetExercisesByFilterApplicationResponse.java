package org.avillar.gymtracker.exercisesapi.exercise.application.get.getexercisesbyfilter.model;

import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetExercisesByFilterApplicationResponse {
  private UUID id;
  private String name;
  private String description;
  private LoadType loadType;
  private Set<MuscleSubGroup> muscleSubGroups;
  private Set<MuscleGroupExercise> muscleGroupExercises;

  @Data
  @NoArgsConstructor
  public static class LoadType {
    private UUID id;
    private String name;
    private String description;
  }

  @Data
  @NoArgsConstructor
  public static class MuscleSubGroup {
    private UUID id;
    private String name;
    private String description;
  }

  @Data
  @NoArgsConstructor
  public static class MuscleGroupExercise {
    private UUID id;
    private Double weight;
    private MuscleGroup muscleGroup;

    @Data
    @NoArgsConstructor
    public static class MuscleGroup {
      private UUID id;
      private String name;
      private String description;
      private Set<MuscleSupGroup> muscleSupGroups;

      @Data
      @NoArgsConstructor
      public static class MuscleSupGroup {
        private UUID id;
        private String name;
        private String description;
      }
    }
  }
}
