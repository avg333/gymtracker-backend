package org.avillar.gymtracker.exercisesapi.common.adapter.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Exercise")
public class ExerciseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private String name;

  @Column private String description;

  @Column(nullable = false)
  private AccessTypeEnum accessType = AccessTypeEnum.PUBLIC;

  @Column(nullable = false)
  private UUID owner;

  @Column(nullable = false)
  private Boolean unilateral = false;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "load_type_id", nullable = false)
  private LoadTypeEntity loadType;

  @ManyToMany(mappedBy = "exercises", fetch = FetchType.LAZY)
  private Set<MuscleSubGroupEntity> muscleSubGroups = new HashSet<>();

  @OneToMany(mappedBy = "exercise", orphanRemoval = true, fetch = FetchType.LAZY)
  private Set<MuscleGroupExerciseEntity> muscleGroupExercises = new HashSet<>();

  @OneToMany(mappedBy = "exercise", orphanRemoval = true)
  private Set<ExerciseUsesEntity> exerciseUses = new HashSet<>();

  public UUID getUserId() {
    return this.getOwner();
  }
}
