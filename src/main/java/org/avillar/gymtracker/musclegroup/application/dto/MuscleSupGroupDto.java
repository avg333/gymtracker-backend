package org.avillar.gymtracker.musclegroup.application.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class MuscleSupGroupDto implements Serializable {

    private Long id;
    private String name;
    private String description;

    private List<MuscleGroupDto> muscleGroups;

}
