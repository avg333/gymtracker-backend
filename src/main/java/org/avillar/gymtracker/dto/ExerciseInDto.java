package org.avillar.gymtracker.dto;

import lombok.Data;

@Data
public class ExerciseInDto {

    private Long id;
    private String name;
    private String description;
    private Boolean unilateral;
    private Long loadType;
    private Long muscleGroups;
    private Long muscleSubGroups;
}
