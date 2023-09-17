package org.avillar.gymtracker.exercisesapi.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
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
public class ExerciseUses extends BaseEntity {

  private Integer uses;

  private UUID userId;

  @ManyToOne
  @JoinColumn(name = "exercise_id")
  private Exercise exercise;
}
