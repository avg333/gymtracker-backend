package org.avillar.gymtracker.musclegroup.application.dto;

import lombok.Data;

import java.io.Serializable;


@Data
public class MuscleSubGroupDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private MuscleGroupDto muscleGroup;
    //private List<ExerciseDto> exercises; NO SE USA

}
