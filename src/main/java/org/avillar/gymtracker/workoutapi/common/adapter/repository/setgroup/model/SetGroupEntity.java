package org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    indexes = {
      @Index(name = "idx_set_workout_relation", columnList = "workout_id"),
      @Index(name = "idxWorkoutIdExerciseId", columnList = "workout_id, exercise_id")
    })
public class SetGroupEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @With
  private UUID id;

  @OrderBy
  @Column(nullable = false)
  private Integer listOrder;

  @Column private String description;

  @Column(name = "exercise_id", nullable = false)
  private UUID exerciseId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "workout_id", nullable = false)
  private WorkoutEntity workout;

  @OneToMany(
      mappedBy = "setGroup",
      cascade = CascadeType.REMOVE,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  @OrderBy("listOrder ASC")
  private Set<SetEntity> sets = new HashSet<>();

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
}
