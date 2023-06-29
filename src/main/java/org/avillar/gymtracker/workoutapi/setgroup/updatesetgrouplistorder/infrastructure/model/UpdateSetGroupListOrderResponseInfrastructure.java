package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder.infrastructure.model;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSetGroupListOrderResponseInfrastructure {

  private Set<SetGroup> setGroups;

  @Data
  public static class SetGroup {
    private UUID id;
    private Integer listOrder;
    private String description;
    private UUID exerciseId;
  }
}
