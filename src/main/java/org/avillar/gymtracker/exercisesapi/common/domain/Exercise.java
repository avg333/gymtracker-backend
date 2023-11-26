package org.avillar.gymtracker.exercisesapi.common.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import org.avillar.gymtracker.common.auth.AuthenticatedEntity;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;

@Getter
@Setter
@Builder
public class Exercise implements AuthenticatedEntity {

  private UUID id;

  private String name;

  private String description;

  @Default private AccessTypeEnum accessType = AccessTypeEnum.PUBLIC;

  private UUID owner;

  @Default private Boolean unilateral = false;

  private LoadType loadType;

  @Default private List<MuscleSubGroup> muscleSubGroups = new ArrayList<>();

  @Default private List<MuscleGroupExercise> muscleGroupExercises = new ArrayList<>();

  @Default private List<ExerciseUses> exerciseUses = new ArrayList<>();

  public UUID getUserId() {
    return this.getOwner();
  }
}
