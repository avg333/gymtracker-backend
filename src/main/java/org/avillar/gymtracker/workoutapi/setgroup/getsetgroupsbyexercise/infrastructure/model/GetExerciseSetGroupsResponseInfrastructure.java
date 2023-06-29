package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.infrastructure.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetExerciseSetGroupsResponseInfrastructure {

  private List<SetGroup> setGroups;

  @Data
  @AllArgsConstructor
  public static class SetGroup {
    private UUID id;
    private Integer listOrder;
    private String description;
    private UUID exerciseId;
    private Workout workout;

    @Data
    @AllArgsConstructor
    public static class Workout {
      private UUID id;
    }
  }
}
