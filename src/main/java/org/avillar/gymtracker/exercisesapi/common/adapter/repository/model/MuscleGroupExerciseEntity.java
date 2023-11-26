package org.avillar.gymtracker.exercisesapi.common.adapter.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MuscleGroupExercise")
public class MuscleGroupExerciseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private Double weight;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "muscle_group_id", nullable = false)
  private MuscleGroupEntity muscleGroup;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "exercise_id", nullable = false)
  private ExerciseEntity exercise;
}
