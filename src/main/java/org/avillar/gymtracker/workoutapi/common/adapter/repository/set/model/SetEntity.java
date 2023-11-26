package org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Timestamp;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    indexes = {@Index(name = "idx_set_group_relation", columnList = "set_group_id")},
    name = "set_table")
public class SetEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @Column(nullable = false)
  private Integer listOrder;

  @Column private String description;

  @Column(precision = 3)
  private Integer reps;

  @Column(precision = 3)
  private Double rir;

  @Column(precision = 7)
  private Double weight;

  @Temporal(TemporalType.TIMESTAMP)
  private Timestamp completedAt;

  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  @JoinColumn(name = "set_group_id", nullable = false)
  private SetGroupEntity setGroup;
}
