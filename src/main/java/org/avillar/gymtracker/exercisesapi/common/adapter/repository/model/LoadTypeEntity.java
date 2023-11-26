package org.avillar.gymtracker.exercisesapi.common.adapter.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "LoadType")
public class LoadTypeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @With
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column private String description;

  @OneToMany(mappedBy = "loadType", orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<ExerciseEntity> exercises = new HashSet<>();
}
