package org.avillar.gymtracker.exercisesapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import java.util.HashSet;
import java.util.Set;
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
public class MuscleSubGroup extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column private String description;

  @ManyToOne(optional = false)
  @JoinColumn(name = "muscle_group_id", nullable = false)
  private MuscleGroup muscleGroup;

  @ManyToMany
  @JoinTable(
      name = "muscle_sub_group_exercises",
      joinColumns = @JoinColumn(name = "muscle_sub_group_id"),
      inverseJoinColumns = @JoinColumn(name = "exercises_id"))
  private Set<Exercise> exercises = new HashSet<>();
}
