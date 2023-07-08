package org.avillar.gymtracker.workoutapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.util.Date;
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
@Table(
    indexes = {@Index(name = "idxSetGroupId", columnList = "set_group_id")},
    name = "set_table")
public class Set extends SortableEntity {

  @Column private String description;

  @Column(precision = 3)
  private Integer reps;

  @Column(precision = 3)
  private Double rir;

  @Column(precision = 7)
  private Double weight;

  @Temporal(TemporalType.TIMESTAMP)
  private Date completedAt;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "set_group_id", nullable = false)
  private SetGroup setGroup;

  public Set(String description, Integer reps, Double rir, Double weight, SetGroup setGroup) {
    this.description = description;
    this.reps = reps;
    this.rir = rir;
    this.weight = weight;
    this.setGroup = setGroup;
  }

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
