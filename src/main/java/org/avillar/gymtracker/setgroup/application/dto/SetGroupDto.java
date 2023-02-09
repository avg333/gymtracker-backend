package org.avillar.gymtracker.setgroup.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.avillar.gymtracker.base.application.BaseDto;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.session.application.dto.SessionDto;
import org.avillar.gymtracker.set.application.dto.SetDto;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SetGroupDto extends BaseDto {
    private int listOrder;
    private String description;

    private ExerciseDto exercise;
    private SessionDto session;
    private WorkoutDto workout;

    private List<SetDto> sets;

    public SetGroupDto(Long id) {
        super(id);
    }

}
