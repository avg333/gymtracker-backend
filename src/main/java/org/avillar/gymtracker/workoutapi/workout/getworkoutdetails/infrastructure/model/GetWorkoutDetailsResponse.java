package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetWorkoutDetailsResponse {

  private UUID id;
  private Date date;
  private String description;
  private UUID userId;
  private List<SetGroup> setGroups;

  @Data
  public static class SetGroup {

    private UUID id;
    private Integer listOrder;
    private String description;
    private UUID exerciseId;
    private List<Set> sets;

    private Exercise exercise;

    @Data
    public static class Set {

      private UUID id;
      private Integer listOrder;
      private String description;
      private Integer reps;
      private Double rir;
      private Double weight;
      private Date completedAt;
    }

    @Data
    public static class Exercise {

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
  }
}
