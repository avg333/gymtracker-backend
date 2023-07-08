package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.model;

import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetAllMuscleGroupsByMuscleSupGroupResponseApplication {

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
