package org.avillar.gymtracker.musclegroup.application;

import lombok.Data;


@Data
public class MuscleGroupDto {

    private Long id;
    private String name;
    private String description;
    private Double volume = 0.0;

}
