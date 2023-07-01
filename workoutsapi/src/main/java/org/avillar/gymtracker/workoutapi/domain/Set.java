package org.avillar.gymtracker.workoutapi.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.common.sort.domain.SortableEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {@Index(name = "setGroupId", columnList = "set_group_id")}, name = "set_table")
public class Set extends SortableEntity {

  private String description;
  private Integer reps;
  private Double rir;
  private Double weight;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "set_group_id", nullable = false)
  private SetGroup setGroup;

  public static Set clone(final Set set) {
    final Set newSet = new Set();
    newSet.setListOrder(set.getListOrder());
    newSet.setDescription(set.getDescription());
    newSet.setReps(set.getReps());
    newSet.setRir(set.getRir());
    newSet.setWeight(set.getWeight());
    return newSet;
  }
}
