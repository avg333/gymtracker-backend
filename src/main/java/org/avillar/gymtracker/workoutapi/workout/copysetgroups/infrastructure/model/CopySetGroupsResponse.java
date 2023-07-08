package org.avillar.gymtracker.workoutapi.workout.copysetgroups.infrastructure.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class CopySetGroupsResponse {

  private UUID id;
  private Integer listOrder;
  private String description;
  private UUID exerciseId;
  private List<Set> sets;

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
}
