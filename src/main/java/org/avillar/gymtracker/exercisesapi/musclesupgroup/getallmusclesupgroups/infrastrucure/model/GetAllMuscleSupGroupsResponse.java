package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.infrastrucure.model;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetAllMuscleSupGroupsResponse {

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
