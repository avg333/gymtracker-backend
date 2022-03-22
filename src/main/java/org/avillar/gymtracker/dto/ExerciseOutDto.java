package org.avillar.gymtracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExerciseOutDto {

    private Long id;
    private String name;
    private String description;
    private Boolean unilateral;
    private String loadType;
    private String muscleGroups;
    private String muscleSubGroups;

}
