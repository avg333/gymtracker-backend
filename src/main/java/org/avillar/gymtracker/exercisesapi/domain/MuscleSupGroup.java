package org.avillar.gymtracker.exercisesapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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

  @Column(nullable = false)
  private String name;

  @Column private String description;

  @ManyToMany(mappedBy = "muscleSupGroups", fetch = FetchType.LAZY)
  private Set<MuscleGroup> muscleGroups = new HashSet<>();
}
