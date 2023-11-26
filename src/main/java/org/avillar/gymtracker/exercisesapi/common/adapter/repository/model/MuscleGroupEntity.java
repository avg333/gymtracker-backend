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
import jakarta.persistence.OneToMany;
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
@Entity(name = "MuscleGroup")
public class MuscleGroupEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column private String description;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "muscle_sup_group_muscle_groups",
      joinColumns = @JoinColumn(name = "muscle_sup_group_id"),
      inverseJoinColumns = @JoinColumn(name = "muscle_groups_id"))
  private Set<MuscleSupGroupEntity> muscleSupGroups = new HashSet<>();

  @OneToMany(mappedBy = "muscleGroup", orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<MuscleSubGroupEntity> muscleSubGroups = new HashSet<>();

  @OneToMany(mappedBy = "muscleGroup", orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<MuscleGroupExerciseEntity> muscleGroupExercises = new HashSet<>();
}
