package org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model;

import static jakarta.persistence.TemporalType.DATE;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.With;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.hibernate.annotations.BatchSize;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    indexes = {
      @Index(name = "idxUserId", columnList = "user_id"),
      @Index(name = "idxUserDate", columnList = "user_id, workout_date", unique = true)
    })
public class WorkoutEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @With
  private UUID id;

  @Column(name = "workout_date", nullable = false)
  @Temporal(DATE)
  private Date date;

  @Column private String description;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @OneToMany(
      mappedBy = "workout",
      cascade = CascadeType.REMOVE,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  @OrderBy("listOrder ASC")
  @BatchSize(size = 20)
  private Set<SetGroupEntity> setGroups = new HashSet<>();
}
