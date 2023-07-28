package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.model;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import lombok.Data;

@Data
public class GetAllMuscleGroupsByMuscleSupGroupResponseApplication implements Serializable {

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
