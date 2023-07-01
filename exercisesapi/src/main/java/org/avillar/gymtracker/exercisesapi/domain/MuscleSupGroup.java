package org.avillar.gymtracker.exercisesapi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
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
public class MuscleSupGroup extends BaseEntity {
  private String name;
  private String description;

  @ManyToMany(mappedBy = "muscleSupGroups")
  private Set<MuscleGroup> muscleGroups = new HashSet<>();
}
