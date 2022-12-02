package org.avillar.gymtracker.musclegroup.application.dto;

import lombok.Data;

import java.util.Set;


@Data
public class MuscleSupGroupDto {

    private Long id;
    private String name;
    private String description;

    private Set<MuscleGroupDto> muscleGroups;

}
