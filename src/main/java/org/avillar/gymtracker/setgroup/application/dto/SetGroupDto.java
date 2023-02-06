package org.avillar.gymtracker.setgroup.application.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.session.application.dto.SessionDto;
import org.avillar.gymtracker.set.application.dto.SetDto;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;

import java.util.List;

@Data
@NoArgsConstructor
public class SetGroupDto {
    private Long id;
    private String description;
    private int listOrder;

    private ExerciseDto exercise;
    private SessionDto session;
    private WorkoutDto workout;

    private List<SetDto> sets;

    public SetGroupDto(Long id) {
        this.id = id;
    }
}
