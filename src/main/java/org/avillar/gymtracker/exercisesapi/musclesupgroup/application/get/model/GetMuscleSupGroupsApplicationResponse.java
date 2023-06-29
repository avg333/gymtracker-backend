package org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model;

import java.util.List;
import java.util.UUID;
import lombok.Data;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.model.GetMuscleSupGroupsInfrastructureResponse.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.infrastructure.get.model.GetMuscleSupGroupsInfrastructureResponse.MuscleGroup.MuscleSubGroup;

@Data
public class GetMuscleSupGroupsApplicationResponse {

  private UUID id;
  private String name;
  private String description;
  private List<MuscleGroup> muscleGroups;

  @Data
  public static class MuscleGroup {
    private UUID id;
    private String name;
    private String description;
    private List<MuscleSubGroup> muscleSubGroups;

    @Data
    public static class MuscleSubGroup {
      private UUID id;
      private String name;
      private String description;
    }
  }
}
