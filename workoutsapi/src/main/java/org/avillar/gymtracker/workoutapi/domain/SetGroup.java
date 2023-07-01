package org.avillar.gymtracker.workoutapi.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.common.sort.domain.SortableEntity;
import org.hibernate.annotations.BatchSize;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    indexes = {
      @Index(name = "workoutId", columnList = "workout_id")
    } // TODO Añadir indice workoutId exerciseId
    )
public class SetGroup extends SortableEntity {

  @Column private String description;

  @Column(nullable = false)
  private UUID exerciseId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "workout_id", nullable = false)
  private Workout workout;

  @OneToMany(
      mappedBy = "setGroup",
      cascade = CascadeType.REMOVE,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  @OrderBy("listOrder ASC")
  @BatchSize(size = 20)
  private java.util.Set<Set> sets = new HashSet<>();

  public static SetGroup clone(final SetGroup setGroup) {
    final SetGroup newSetGroup = new SetGroup();
    newSetGroup.setListOrder(setGroup.getListOrder());
    newSetGroup.setDescription(setGroup.getDescription());
    newSetGroup.setExerciseId(setGroup.getExerciseId());
    return newSetGroup;
  }
}
