package org.avillar.gymtracker.exercisesapi.exercise.getexercisesbyids.application.model;

import java.util.Set;
import java.util.UUID;
import lombok.Data;

@Data
public class GetExercisesByIdsResponseApplication {
  private UUID id;
  private String name;
  private String description;
  private LoadType loadType;
  private Set<MuscleSubGroup> muscleSubGroups;
  private Set<MuscleGroupExercise> muscleGroupExercises;

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
  public static class MuscleGroupExercise {
    private UUID id;
    private Double weight;
    private MuscleGroup muscleGroup;

    @Data
    public static class MuscleGroup {
      private UUID id;
      private String name;
      private String description;
      private Set<MuscleSupGroup> muscleSupGroups;

      @Data
      public static class MuscleSupGroup {
        private UUID id;
        private String name;
        private String description;
      }
    }
  }
}
