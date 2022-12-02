package org.avillar.gymtracker.workout.application;

import lombok.Data;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;

import java.util.Date;
import java.util.List;

@Data
public class WorkoutDto {
    private Long id;
    private Date date;
    private String description;
    private Long userId;

    // Para el calendario
    private List<MuscleGroupDto> muscleGroupDtos;

    // Para el summary
    private Integer exerciseNumber;
    private Integer setsNumber;
    private Integer weightVolume;
    private Integer duration;
}
