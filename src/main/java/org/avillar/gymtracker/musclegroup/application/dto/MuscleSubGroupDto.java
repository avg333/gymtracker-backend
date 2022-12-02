package org.avillar.gymtracker.musclegroup.application.dto;

import lombok.Data;


@Data
public class MuscleSubGroupDto {

    private Long id;
    private String name;
    private String description;
    // private MuscleGroupDto muscleGroup; FIXME Dependencia circular

}
