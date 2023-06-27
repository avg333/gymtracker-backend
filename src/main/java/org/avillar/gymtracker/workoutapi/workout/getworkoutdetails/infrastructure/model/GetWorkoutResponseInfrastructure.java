package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetWorkoutResponseInfrastructure {

  private UUID id;
  private Date date;
  private String description;
  private UUID userId;
  private List<SetGroup> setGroups;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SetGroup {
    private UUID id;
    private Integer listOrder;
    private String description;
    private UUID exerciseId;
    private List<Set> sets;

    private Exercise exercise;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Set {
    private UUID id;
    private Integer listOrder;
    private String description;
    private Integer reps;
    private Double rir;
    private Double weight;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Exercise {
    private UUID id;
    private String name;
    private String description;
    private LoadType loadType;
    private java.util.Set<MuscleSubGroup> muscleSubGroups;
    private java.util.Set<MuscleGroupExercise> muscleGroupExercises;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class LoadType {
    private UUID id;
    private String name;
    private String description;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MuscleSubGroup {
    private UUID id;
    private String name;
    private String description;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MuscleGroupExercise {
    private UUID id;
    private Double weight;
    private MuscleGroup muscleGroup;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MuscleGroup {
    private UUID id;
    private String name;
    private String description;
    private java.util.Set<MuscleSupGroup> muscleSupGroups;
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class MuscleSupGroup {
    private UUID id;
    private String name;
    private String description;
  }
}
