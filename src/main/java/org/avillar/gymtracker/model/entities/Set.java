package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Set extends BaseEntity {

    private String description;
    @Column(nullable = false)
    private Integer listOrder;
    private Integer reps;
    private Double rir;
    private Double weight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "set_group_id", nullable = false)
    private SetGroup setGroup;
}