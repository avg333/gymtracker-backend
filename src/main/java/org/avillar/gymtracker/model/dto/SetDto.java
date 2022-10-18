package org.avillar.gymtracker.model.dto;

import lombok.Data;


@Data
public class SetDto {

    private Long id;
    private String description;
    private int reps;
    private double rir;
    private int listOrder;
    private double weight;
    private Long setGroupId;
}
