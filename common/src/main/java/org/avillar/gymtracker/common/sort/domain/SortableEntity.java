package org.avillar.gymtracker.common.sort.domain;

import org.avillar.gymtracker.common.base.domain.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public abstract class SortableEntity extends BaseEntity {

  @OrderBy
  @Column(nullable = false)
  private Integer listOrder;
}
