package org.avillar.gymtracker.session.application.dto;

import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;
import org.avillar.gymtracker.workout.domain.Workout;

import java.util.Collection;
import java.util.List;

public interface SessionMapper {

    List<SessionDto> toDtos(Collection<Session> workouts, boolean nested);

    SessionDto toDto(Session workout, boolean nested);

    Session toEntity(SessionDto workoutDto);

}
