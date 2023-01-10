package org.avillar.gymtracker.exercise.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseFilterDto {

    private String name;
    private String description;
    private Boolean unilateral;
    private LoadTypeEnum loadType;
    private List<Long> muscleSupGroupIds;
    private List<Long> muscleGroupIds;
    private List<Long> muscleSubGroupIds;
}
