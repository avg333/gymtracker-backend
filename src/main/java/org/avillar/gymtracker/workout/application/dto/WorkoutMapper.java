package org.avillar.gymtracker.workout.application.dto;

import org.avillar.gymtracker.workout.domain.Workout;

import java.util.Collection;
import java.util.List;

public interface WorkoutMapper {

    List<WorkoutDto> toDtos(Collection<Workout> workouts, boolean nested);

    WorkoutDto toDto(Workout workout, boolean nested);

    Workout toEntity(WorkoutDto workoutDto);

}
