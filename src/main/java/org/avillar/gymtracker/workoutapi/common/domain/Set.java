package org.avillar.gymtracker.workoutapi.common.domain;

import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.avillar.gymtracker.workoutapi.common.sort.domain.SortableEntity;

@Getter
@Setter
@Builder
public class Set implements SortableEntity {

  private UUID id;

  private Integer listOrder;

  private String description;

  private Integer reps;

  private Double rir;

  private Double weight;

  private Date completedAt;

  private SetGroup setGroup;

  public Set createCopy() {
    return Set.builder()
        .listOrder(this.getListOrder())
        .description(this.getDescription())
        .reps(this.getReps())
        .rir(this.getRir())
        .weight(this.getWeight())
        .build();
  }
}
