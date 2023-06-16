package org.avillar.gymtracker.exercisesapi.loadtype.domain;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LoadType extends BaseEntity {

  @Column(nullable = false)
  private String name;

  @Column private String description;

  @OneToMany(mappedBy = "loadType", orphanRemoval = true)
  private Set<Exercise> exercises = new HashSet<>();
}
