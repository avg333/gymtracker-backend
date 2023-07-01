package org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetWorkoutDetailsResponseInfrastructure {

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
  }

  @Data
  public static class Set {
    private UUID id;
    private Integer listOrder;
    private String description;
    private Integer reps;
    private Double rir;
    private Double weight;
  }

  @Data
  public static class Exercise {
    private UUID id;
    private String name;
    private String description;
    private LoadType loadType;
    private java.util.Set<MuscleSubGroup> muscleSubGroups;
    private java.util.Set<MuscleGroupExercise> muscleGroupExercises;
  }

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
  }

  @Data
  public static class MuscleGroup {
    private UUID id;
    private String name;
    private String description;
    private java.util.Set<MuscleSupGroup> muscleSupGroups;
  }

  @Data
  public static class MuscleSupGroup {
    private UUID id;
    private String name;
    private String description;
  }
}
