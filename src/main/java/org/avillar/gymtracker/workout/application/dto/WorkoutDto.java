package org.avillar.gymtracker.workout.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.application.BaseDto;
import org.avillar.gymtracker.musclegroup.application.dto.MuscleGroupDto;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.avillar.gymtracker.user.application.UserAppDto;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class WorkoutDto extends BaseDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date date;
    private String description;

    private UserAppDto userApp;

    private List<SetGroupDto> setGroups;

    // Para el summary
    private List<MuscleGroupDto> muscleGroupDtos;
    private Integer exerciseNumber;
    private Integer setsNumber;
    private Integer weightVolume;
    private Integer duration;

    public WorkoutDto(Long id) {
        super(id);
    }

}
