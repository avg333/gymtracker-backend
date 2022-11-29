package org.avillar.gymtracker.exercise.application;

import lombok.Data;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;
import org.avillar.gymtracker.musclegroup.application.MuscleGroupDto;
import org.avillar.gymtracker.musclegroup.application.MuscleSubGroupDto;
import org.avillar.gymtracker.musclegroup.application.MuscleSupGroupDto;

import java.util.List;

@Data
public class ExerciseDto {

    private Long id;
    private String name;
    private String description;
    private Boolean unilateral;
    private LoadTypeEnum loadType;

    private List<MuscleSupGroupDto> muscleSupGroups;
    private List<MuscleGroupDto> muscleGroups;
    private List<MuscleSubGroupDto> muscleSubGroups;
}
