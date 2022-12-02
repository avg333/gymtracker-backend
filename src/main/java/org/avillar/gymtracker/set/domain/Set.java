package org.avillar.gymtracker.set.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.setgroup.domain.SetGroup;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Set extends BaseEntity {

    private String description;
    @OrderBy
    @Column(nullable = false)
    private Integer listOrder;
    private Integer reps;
    private Double rir;
    private Double weight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "set_group_id", nullable = false)
    private SetGroup setGroup;
}