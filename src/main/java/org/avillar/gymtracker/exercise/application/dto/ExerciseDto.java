package org.avillar.gymtracker.exercise.application.dto;

import lombok.Data;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleSubGroupDto;

import java.io.Serializable;
import java.util.List;

@Data
public class ExerciseDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private Boolean unilateral;
    private LoadTypeEnum loadType;

    private List<MuscleGroupExerciseDto> muscleGroupExercises;
    private List<MuscleSubGroupDto> muscleSubGroups;

    //private Set<SetGroup> setGroups NO SE USA

    public ExerciseDto(Long id){
        this.id = id;
    }

}
