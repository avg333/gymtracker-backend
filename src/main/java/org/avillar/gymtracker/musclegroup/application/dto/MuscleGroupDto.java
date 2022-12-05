package org.avillar.gymtracker.musclegroup.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class MuscleGroupDto implements Serializable {

    private Long id;
    private String name;
    private String description;

    private List<MuscleSupGroupDto> muscleSupGroups;
    private List<MuscleSubGroupDto> muscleSubGroups;
    //private List<MuscleGroupExerciseDto> muscleGroupExercises; NO SE USA

    private Double volume = 0.0;

}
