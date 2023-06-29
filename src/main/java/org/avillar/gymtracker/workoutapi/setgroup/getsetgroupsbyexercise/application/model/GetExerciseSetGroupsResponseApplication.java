package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise.application.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetExerciseSetGroupsResponseApplication {

  private List<SetGroup> setGroups;

  @Data
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
