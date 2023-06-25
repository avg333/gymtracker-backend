package org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.model;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetMuscleSupGroupsInfrastructureResponse {

  private UUID id;
  private String name;
  private String description;
  private List<MuscleGroup> muscleGroups;

  @Data
  @NoArgsConstructor
  public static class MuscleGroup {
    private UUID id;
    private String name;
    private String description;
    private List<MuscleSubGroup> muscleSubGroups;

    @Data
    @NoArgsConstructor
    public static class MuscleSubGroup {
      private UUID id;
      private String name;
      private String description;
    }
  }
}
