package org.avillar.gymtracker.exercise.application;

import lombok.Data;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSubGroupDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSupGroupDto;

import java.util.Set;

@Data
public class ExerciseDto {

    private Long id;
    private String name;
    private String description;
    private Boolean unilateral;
    private LoadTypeEnum loadType;

    private Set<MuscleSubGroupDto> muscleSubGroups;

    private Set<MuscleSupGroupDto> muscleSupGroups;
    private Set<MuscleGroupDto> muscleGroups;
}
