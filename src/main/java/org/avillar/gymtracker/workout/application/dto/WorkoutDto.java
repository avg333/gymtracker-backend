package org.avillar.gymtracker.workout.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.avillar.gymtracker.user.application.UserAppDto;

import java.util.Date;
import java.util.List;

@Data
public class WorkoutDto {
    private Long id;
    //@JsonFormat(pattern="yyyy-MM-dd")
    private Date date;
    private String description;

    private UserAppDto userApp;

    private List<SetGroupDto> setGroups;

    // Para el calendario
    private List<MuscleGroupDto> muscleGroupDtos;

    // Para el summary
    private Integer exerciseNumber;
    private Integer setsNumber;
    private Integer weightVolume;
    private Integer duration;
}
