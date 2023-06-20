package org.avillar.gymtracker.exercisesapi.musclegroup.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.domain.MuscleSupGroup;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MuscleGroup extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column private String description;

  @ManyToMany
  @JoinTable(
      name = "muscle_sup_group_muscle_groups",
      joinColumns = @JoinColumn(name = "muscle_sup_group_id"),
      inverseJoinColumns = @JoinColumn(name = "muscle_groups_id"))
  private Set<MuscleSupGroup> muscleSupGroups = new HashSet<>();

  @OneToMany(mappedBy = "muscleGroup", orphanRemoval = true)
  private Set<MuscleSubGroup> muscleSubGroups = new HashSet<>();

  @OneToMany(mappedBy = "muscleGroup", orphanRemoval = true)
  private Set<MuscleGroupExercise> muscleGroupExercises = new HashSet<>();
}
