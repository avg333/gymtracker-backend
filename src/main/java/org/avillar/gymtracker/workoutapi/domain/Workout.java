package org.avillar.gymtracker.workoutapi.domain;

import static jakarta.persistence.TemporalType.DATE;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.hibernate.annotations.BatchSize;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    indexes = {
      @Index(name = "userDate", columnList = "user_id"),
      @Index(name = "userDate", columnList = "user_id, date", unique = true)
    })
public class Workout extends BaseEntity {

  @Column(name = "date", nullable = false)
  @Temporal(DATE)
  private Date date;

  @Column
  private String description;

  @Column(name = "user_id", nullable = false)
  private UUID userId;

  @OneToMany(
      mappedBy = "workout",
      cascade = CascadeType.REMOVE,
      orphanRemoval = true,
      fetch = FetchType.LAZY)
  @OrderBy("listOrder ASC")
  @BatchSize(size = 20)
  private Set<SetGroup> setGroups = new HashSet<>();
}
