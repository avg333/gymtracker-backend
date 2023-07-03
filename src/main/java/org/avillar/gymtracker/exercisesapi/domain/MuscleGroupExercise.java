package org.avillar.gymtracker.exercisesapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.common.base.domain.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MuscleGroupExercise extends BaseEntity {

  @Column(nullable = false)
  private Double weight;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "muscle_group_id", nullable = false)
  private MuscleGroup muscleGroup;

  @ManyToOne(optional = false)
  @JoinColumn(name = "exercise_id", nullable = false)
  private Exercise exercise;
}
