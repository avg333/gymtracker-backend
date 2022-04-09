package org.avillar.gymtracker.dto;

import lombok.Data;
import org.avillar.gymtracker.model.Exercise;


@Data
public class SetDto {

    private Long id;
    private String name;
    private String description;
    private Exercise exercise;
    private double weight;
    private int reps;
    private double rir;
    private int setOrder;
}
