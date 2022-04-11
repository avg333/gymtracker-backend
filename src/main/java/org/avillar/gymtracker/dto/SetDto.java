package org.avillar.gymtracker.dto;

import lombok.Data;


@Data
public class SetDto {

    private Long id;
    private String description;
    private int reps;
    private double rir;
    private int setOrder;
    private double weight;
}
