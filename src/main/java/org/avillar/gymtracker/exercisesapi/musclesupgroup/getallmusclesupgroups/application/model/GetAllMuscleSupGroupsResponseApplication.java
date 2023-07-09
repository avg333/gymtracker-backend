package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetAllMuscleSupGroupsResponseApplication implements Serializable {

  private UUID id;
  private String name;
  private String description;
  private List<MuscleGroup> muscleGroups;

  @Data
  public static class MuscleGroup implements Serializable {

    private UUID id;
    private String name;
    private String description;
    private List<MuscleSubGroup> muscleSubGroups;

    @Data
    public static class MuscleSubGroup implements Serializable {

      private UUID id;
      private String name;
      private String description;
    }
  }
}
