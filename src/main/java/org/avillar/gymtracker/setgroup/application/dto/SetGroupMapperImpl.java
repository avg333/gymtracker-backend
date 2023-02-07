package org.avillar.gymtracker.setgroup.application.dto;

import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.exercise.application.dto.ExerciseMapper;
import org.avillar.gymtracker.session.application.dto.SessionDto;
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
    public List<SetGroupDto> toDtos(final Collection<SetGroup> setGroups, final int depth) {
        if (CollectionUtils.isEmpty(setGroups)) {
            return Collections.emptyList();
        }

        return setGroups.stream().map(setGroup -> this.toDto(setGroup, depth)).toList();
    }

    @Override
    public List<SetGroup> toEntities(final Collection<SetGroupDto> setGroupDtos) {
        if (CollectionUtils.isEmpty(setGroupDtos)) {
            return Collections.emptyList();
        }

        return setGroupDtos.stream().map(this::toEntity).toList();
    }

    @Override
    public SetGroupDto toDto(final SetGroup setGroup, final int depth) {
        if (setGroup == null) {
            return null;
        }

        final SetGroupDto setGroupDto = new SetGroupDto();
        setGroupDto.setId(setGroup.getId());
        setGroupDto.setListOrder(setGroup.getListOrder());
        setGroupDto.setDescription(setGroup.getDescription());

        setGroupDto.setExercise(depth != 0
                ? this.exerciseMapper.toDto(setGroup.getExercise(), depth - 1)
                : null);

        if (depth != 0 && BaseEntity.exists(setGroup.getSession())) {
            setGroupDto.setSession(new SessionDto(setGroup.getSession().getId()));
        }
        if (depth != 0 && BaseEntity.exists(setGroup.getWorkout())) {
            setGroupDto.setWorkout(new WorkoutDto(setGroup.getWorkout().getId()));
        }

        setGroupDto.setSets(depth != 0
                ? this.setMapper.toDtos(setGroup.getSets(), depth - 1)
                : Collections.emptyList());

        return setGroupDto;
    }

    @Override
    public SetGroup toEntity(final SetGroupDto setGroupDto) {
        if (setGroupDto == null) {
            return null;
        }

        final SetGroup setGroup = new SetGroup();
        setGroup.setId(setGroupDto.getId());
        setGroup.setListOrder(setGroupDto.getListOrder());
        setGroup.setDescription(setGroupDto.getDescription());

        setGroup.setExercise(this.exerciseMapper.toEntity(setGroupDto.getExercise()));

        if (setGroupDto.getSession() != null && setGroupDto.getSession().getId() != null) {
            setGroup.setSession(new Session(setGroupDto.getSession().getId()));
        }
        if (setGroupDto.getWorkout() != null && setGroupDto.getWorkout().getId() != null) {
            setGroup.setWorkout(new Workout(setGroupDto.getWorkout().getId()));
        }

        setGroup.setSets(new HashSet<>(this.setMapper.toEntities(setGroupDto.getSets())));

        return setGroup;
    }

}
