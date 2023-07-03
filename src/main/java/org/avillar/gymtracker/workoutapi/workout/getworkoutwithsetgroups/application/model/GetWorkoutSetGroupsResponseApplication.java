package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups.application.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetWorkoutSetGroupsResponseApplication {

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
  }
}
