package org.avillar.gymtracker.base.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class SortableEntity extends BaseEntity {
    @OrderBy
    @Column(nullable = false)
    private Integer listOrder;
}
