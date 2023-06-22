package org.avillar.gymtracker.workoutapi.setgroup.application.get.exercisesetgroups.model;

import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetExerciseSetGroupsResponseApplication {

  private List<SetGroup> setGroups;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SetGroup {
    private UUID id;
    private Integer listOrder;
    private String description;
    private UUID exerciseId;
    private Workout workout;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Workout {
      private UUID id;
    }
  }
}
