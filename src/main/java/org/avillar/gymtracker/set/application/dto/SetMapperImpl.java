package org.avillar.gymtracker.set.application.dto;

import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.set.domain.Set;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class SetMapperImpl implements SetMapper {

    @Override
    public List<SetDto> toDtos(final Collection<Set> sets, final int depth) {
        if (CollectionUtils.isEmpty(sets)) {
            return Collections.emptyList();
        }

        return sets.stream().map(set -> this.toDto(set, depth)).toList();
    }

    @Override
    public List<Set> toEntities(final Collection<SetDto> setDtos) {
        if (CollectionUtils.isEmpty(setDtos)) {
            return Collections.emptyList();
        }

        return setDtos.stream().map(this::toEntity).toList();
    }

    @Override
    public SetDto toDto(final Set set, final int depth) {
        if (set == null) {
            return null;
        }

        final SetDto setDto = new SetDto();
        setDto.setId(set.getId());
        setDto.setDescription(set.getDescription());
        setDto.setListOrder(set.getListOrder());
        setDto.setReps(set.getReps());
        setDto.setRir(set.getRir());
        setDto.setWeight(set.getWeight());
        setDto.setLastModifiedAt(set.getLastModifiedAt());

        if (depth != 0 && BaseEntity.exists(set.getSetGroup())) {
            setDto.setSetGroup(new SetGroupDto(set.getSetGroup().getId()));
        }

        return setDto;
    }

    @Override
    public Set toEntity(final SetDto setDto) {
        if (setDto == null) {
            return null;
        }

        final Set set = new Set();
        set.setId(setDto.getId());
        set.setDescription(setDto.getDescription());
        set.setListOrder(setDto.getListOrder());
        set.setReps(setDto.getReps());
        set.setRir(setDto.getRir());
        set.setWeight(setDto.getWeight());

        if (setDto.getSetGroup() != null && setDto.getSetGroup().getId() != null) {
            set.setSetGroup(new SetGroup(setDto.getSetGroup().getId()));
        }

        return set;
    }

}
