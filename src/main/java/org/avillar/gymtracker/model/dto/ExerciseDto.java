package org.avillar.gymtracker.model.dto;

import lombok.Data;
import org.avillar.gymtracker.model.enums.LoadTypeEnum;

@Data
public class ExerciseDto {

    private Long id;
    private String name;
    private String description;
    private Boolean unilateral;
    private LoadTypeEnum loadType;
}
