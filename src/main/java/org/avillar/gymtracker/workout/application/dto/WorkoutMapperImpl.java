package org.avillar.gymtracker.workout.application.dto;

import org.avillar.gymtracker.setgroup.application.dto.SetGroupMapper;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.workout.domain.Workout;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
public class WorkoutMapperImpl implements WorkoutMapper {

    private final SetGroupMapper setGroupMapper;

    public WorkoutMapperImpl(SetGroupMapper setGroupMapper) {
        this.setGroupMapper = setGroupMapper;
    }

    @Override
    public List<WorkoutDto> toDtos(final Collection<Workout> workouts, final int depth) {
        if (CollectionUtils.isEmpty(workouts)) {
            return Collections.emptyList();
        }

        return workouts.stream().map(workout -> this.toDto(workout, depth)).toList();
    }

    public List<Workout> toEntities(final Collection<WorkoutDto> workoutDtos) {
        if (CollectionUtils.isEmpty(workoutDtos)) {
            return Collections.emptyList();
        }

        return workoutDtos.stream().map(this::toEntity).toList();
    }

    @Override
    public WorkoutDto toDto(final Workout workout, final int depth) {
        if (workout == null) {
            return null;
        }

        final WorkoutDto workoutDto = new WorkoutDto();
        workoutDto.setId(workout.getId());
        workoutDto.setDate(workout.getDate());
        workoutDto.setDescription(workout.getDescription());
        workoutDto.setUserApp(null);

        if (depth != 0 && !CollectionUtils.isEmpty(workout.getSetGroups())) {
            workoutDto.setSetGroups(this.setGroupMapper.toDtos(workout.getSetGroups(), depth - 1));
        }

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

        if (workoutDto.getUserApp() != null && workoutDto.getUserApp().getId() != null) {
            workout.setUserApp(new UserApp(workoutDto.getUserApp().getId()));
        }

        if (!CollectionUtils.isEmpty(workoutDto.getSetGroups())) {
            workout.setSetGroups(new HashSet<>(this.setGroupMapper.toEntities(workoutDto.getSetGroups())));
        }

        return workout;
    }
}
