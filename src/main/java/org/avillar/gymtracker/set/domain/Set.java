package org.avillar.gymtracker.set.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.domain.SortableEntity;
import org.avillar.gymtracker.setgroup.domain.SetGroup;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Set extends SortableEntity {

    private String description;
    private Integer reps;
    private Double rir;
    private Double weight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "set_group_id", nullable = false)
    private SetGroup setGroup;
}