package org.avillar.gymtracker.exercisesapi.common.adapter.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "MuscleSubGroup")
public class MuscleSubGroupEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column private String description;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "muscle_group_id", nullable = false)
  private MuscleGroupEntity muscleGroup;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "muscle_sub_group_exercises",
      joinColumns = @JoinColumn(name = "muscle_sub_group_id"),
      inverseJoinColumns = @JoinColumn(name = "exercises_id"))
  private Set<ExerciseEntity> exercises = new HashSet<>();
}
