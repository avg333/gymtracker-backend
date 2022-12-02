package org.avillar.gymtracker.musclegroup.application.dto;

import lombok.Data;

import java.util.Set;


@Data
public class MuscleGroupDto {

    private Long id;
    private String name;
    private String description;

    // private Set<MuscleSupGroupDto> muscleSupGroups; FIXME Dependencia circular
    private Set<MuscleSubGroupDto> muscleSubGroups;

    private Double volume = 0.0;

}
