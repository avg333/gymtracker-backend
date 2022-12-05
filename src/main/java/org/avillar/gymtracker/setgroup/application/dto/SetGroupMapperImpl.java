package org.avillar.gymtracker.setgroup.application.dto;

import org.avillar.gymtracker.exercise.application.dto.ExerciseMapper;
import org.avillar.gymtracker.session.application.SessionDto;
import org.avillar.gymtracker.session.domain.Session;
import org.avillar.gymtracker.set.application.dto.SetMapper;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;
import org.avillar.gymtracker.workout.domain.Workout;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Component
public class SetGroupMapperImpl implements SetGroupMapper {

    private final SetMapper setMapper;
    private final ExerciseMapper exerciseMapper;

    public SetGroupMapperImpl(SetMapper setMapper, ExerciseMapper exerciseMapper) {
        this.setMapper = setMapper;
        this.exerciseMapper = exerciseMapper;
    }

    @Override
    public List<SetGroupDto> toDtos(Collection<SetGroup> setGroups, boolean nested) {
        if (CollectionUtils.isEmpty(setGroups)) {
            return Collections.emptyList();
        }

        return setGroups.stream().map(setGroup -> this.toDto(setGroup, nested)).toList();
    }

    @Override
    public List<SetGroup> toEntities(Collection<SetGroupDto> setGroupDtos) {
        if (CollectionUtils.isEmpty(setGroupDtos)) {
            return Collections.emptyList();
        }

        return setGroupDtos.stream().map(this::toEntity).toList();
    }

    @Override
    public SetGroupDto toDto(final SetGroup setGroup, boolean nested) {
        if (setGroup == null) {
            return null;
        }

        final SetGroupDto setGroupDto = new SetGroupDto();
        setGroupDto.setId(setGroup.getId());
        setGroupDto.setDescription(setGroup.getDescription());
        setGroupDto.setListOrder(setGroup.getListOrder());

        setGroupDto.setSets(this.setMapper.toDtos(setGroup.getSets(), false));
        setGroupDto.setExercise(this.exerciseMapper.toDto(setGroup.getExercise(), false));

        if (setGroup.getSession() != null) {
            final SessionDto sessionDto = new SessionDto();
            sessionDto.setId(setGroup.getSession().getId());
            setGroupDto.setSession(sessionDto);
        }
        if (setGroup.getWorkout() != null) {
            final WorkoutDto workoutDto = new WorkoutDto();
            workoutDto.setId(setGroup.getWorkout().getId());
            setGroupDto.setWorkout(workoutDto);
        }

        return setGroupDto;
    }

    @Override
    public SetGroup toEntity(final SetGroupDto setGroupDto) {
        if (setGroupDto == null) {
            return null;
        }

        final SetGroup setGroup = new SetGroup();
        setGroup.setId(setGroupDto.getId());
        setGroup.setDescription(setGroupDto.getDescription());
        setGroup.setListOrder(setGroupDto.getListOrder());

        setGroup.setSets(new HashSet<>(this.setMapper.toEntities(setGroupDto.getSets())));
        setGroup.setExercise(this.exerciseMapper.toEntity(setGroupDto.getExercise()));

        if (setGroupDto.getSession() != null) {
            final Session session = new Session();
            session.setId(setGroupDto.getSession().getId());
            setGroup.setSession(session);
        }
        if (setGroupDto.getWorkout() != null) {
            final Workout workout = new Workout();
            workout.setId(setGroupDto.getWorkout().getId());
            setGroup.setWorkout(workout);
        }

        return setGroup;
    }
}
