package org.avillar.gymtracker.workoutapi.common.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
import org.avillar.gymtracker.workoutapi.common.sort.domain.SortableEntity;

@Getter
@Setter
@Builder
public class SetGroup implements SortableEntity {

  private UUID id;

  private Integer listOrder;

  private String description;

  private UUID exerciseId;

  private Workout workout;

  @Default private List<Set> sets = new ArrayList<>();

  private Integer rest;

  @Default private Integer eccentric = 3;

  @Default private Integer firstPause = 0;

  @Default private Integer concentric = 1;

  @Default private Integer secondPause = 0;

  @Default private Boolean superSetWithNext = false;

  // ExerciseApiDomain
  private Exercise exercise;

  public SetGroup createCopy() {
    return SetGroup.builder()
        .listOrder(this.getListOrder())
        .description(this.getDescription())
        .exerciseId(this.getExerciseId())
        .build();
  }
}
