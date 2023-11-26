package org.avillar.gymtracker.exercisesapi.common.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MuscleGroup {

  private UUID id;

  private String name;

  private String description;

  @Default private List<MuscleSupGroup> muscleSupGroups = new ArrayList<>();

  @Default private List<MuscleSubGroup> muscleSubGroups = new ArrayList<>();
}
