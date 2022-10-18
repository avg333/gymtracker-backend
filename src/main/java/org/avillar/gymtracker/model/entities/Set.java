package org.avillar.gymtracker.model.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Set extends BaseEntity{

    private String description;
    private int reps;
    private double rir;
    private int setOrder;
    private double weight;

    @ManyToOne(optional = false)
    @JoinColumn(name = "set_group_id", nullable = false)
    private SetGroup setGroup;

}
