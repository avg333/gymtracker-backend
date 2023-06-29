package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.application.model;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSetGroupListOrderResponseApplication {

  private Set<SetGroup> setGroups;

  @Data
  @NoArgsConstructor
  public static class SetGroup {
    private UUID id;
    private Integer listOrder;
    private String description;
    private UUID exerciseId;
  }
}