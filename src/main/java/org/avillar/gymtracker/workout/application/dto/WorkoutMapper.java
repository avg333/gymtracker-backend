package org.avillar.gymtracker.workout.application.dto;

import org.avillar.gymtracker.workout.domain.Workout;

import java.util.Collection;
import java.util.List;

public interface WorkoutMapper {

    List<WorkoutDto> toDtos(Collection<Workout> workouts, int depth);

    WorkoutDto toDto(Workout workout, int depth);

    Workout toEntity(WorkoutDto workoutDto);

}
