package org.avillar.gymtracker.dto;

import lombok.Data;
import org.avillar.gymtracker.model.LoadTypeEnum;

@Data
public class ExerciseDto {

    private Long id;
    private String name;
    private String description;
    private Boolean unilateral;
    private LoadTypeEnum loadTypeEnum;
}
