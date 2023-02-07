package org.avillar.gymtracker.sort.domain;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OrderBy;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class SortableEntity extends BaseEntity {
    @OrderBy
    @Column(nullable = false)
    private Integer listOrder;

    protected SortableEntity(Long id) {
        super(id);
    }
}
