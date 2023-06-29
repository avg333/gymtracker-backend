package org.avillar.gymtracker.exercisesapi.exercise.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.avillar.gymtracker.exercisesapi.loadtype.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.musclegroup.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.domain.MuscleSubGroup;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Exercise extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column private String description;

  @Column(nullable = false)
  private Boolean unilateral = false;

  @ManyToOne(optional = false)
  @JoinColumn(name = "load_type_id", nullable = false)
  private LoadType loadType;

  @ManyToMany(mappedBy = "exercises")
  private Set<MuscleSubGroup> muscleSubGroups = new HashSet<>();

  @OneToMany(mappedBy = "exercise", orphanRemoval = true)
  private Set<MuscleGroupExercise> muscleGroupExercises = new HashSet<>();
}
