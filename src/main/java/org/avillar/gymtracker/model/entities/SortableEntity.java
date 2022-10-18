package org.avillar.gymtracker.model.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class SortableEntity extends BaseEntity {

    @NotNull
    @Column(nullable = false)
    private Integer listOrder;
}
