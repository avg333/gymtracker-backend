package org.avillar.gymtracker.workout.application.dto;

import org.avillar.gymtracker.workout.domain.Workout;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class WorkoutMapperImpl implements WorkoutMapper {


    @Override
    public List<WorkoutDto> toDtos(Collection<Workout> workouts, boolean nested) {
        if (CollectionUtils.isEmpty(workouts)) {
            return Collections.emptyList();
        }

        return workouts.stream().map(workout -> this.toDto(workout, nested)).toList();
    }

    @Override
    public WorkoutDto toDto(final Workout workout, boolean nested) {
        if (workout == null) {
            return null;
        }

        final WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setDate(workout.getDate());
        workoutDto.setDescription(workout.getDescription());

        return workoutDto;
    }

    @Override
    public Workout toEntity(final WorkoutDto workoutDto) {
        if (workoutDto == null) {
            return null;
        }

        final Workout workout = new Workout();
        workout.setId(workoutDto.getId());
        workout.setDate(workoutDto.getDate());
        workout.setDescription(workoutDto.getDescription());

        return workout;
    }
}
