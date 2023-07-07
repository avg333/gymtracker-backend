package org.avillar.gymtracker.workoutapi.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
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

  @Column(precision = 3)
  private Integer rest;

  @Column(precision = 2)
  private Integer eccentric = 3;

  @Column(precision = 2)
  private Integer firstPause = 0;

  @Column(precision = 2)
  private Integer concentric = 1;

  @Column(precision = 2)
  private Integer secondPause = 0;

  @Column(nullable = false)
  private Boolean superSetWithNext = false;

  public SetGroup(String description, UUID exerciseId, Workout workout, java.util.Set<Set> sets) {
    this.description = description;
    this.exerciseId = exerciseId;
    this.workout = workout;
    this.sets = sets;
  }

  public static SetGroup clone(final SetGroup setGroup) {
    final SetGroup newSetGroup = new SetGroup();
    newSetGroup.setListOrder(setGroup.getListOrder());
    newSetGroup.setDescription(setGroup.getDescription());
    newSetGroup.setExerciseId(setGroup.getExerciseId());
    return newSetGroup;
  }
}
