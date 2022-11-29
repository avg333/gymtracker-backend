package org.avillar.gymtracker.exercise.application;

import lombok.Data;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;

import java.util.List;

@Data
public class ExerciseDto {

    private Long id;
    private String name;
    private String description;
    private Boolean unilateral;
    private LoadTypeEnum loadType;

    private List<String> muscleSupGroups;
    private List<String> muscleGroups;
    private List<String> muscleSubGroups;
}
