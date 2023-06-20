package org.avillar.gymtracker.workoutapi.setgroup.infrastructure.update.listorder.model;

import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSetGroupListOrderResponseInfrastructure {

  private Set<SetGroup> setGroups;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SetGroup {
    private UUID id;
    private Integer listOrder;
    private String description;
    private UUID exerciseId;
  }
}
