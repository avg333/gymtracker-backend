package org.avillar.gymtracker.set.application.dto;

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
    public List<SetDto> toDtos(final Collection<Set> sets, final boolean nested) {
        if (CollectionUtils.isEmpty(sets)) {
            return Collections.emptyList();
        }

        return sets.stream().map(set -> this.toDto(set, nested)).toList();
    }

    @Override
    public List<Set> toEntities(final Collection<SetDto> setDtos) {
        if (CollectionUtils.isEmpty(setDtos)) {
            return Collections.emptyList();
        }

        return setDtos.stream().map(this::toEntity).toList();
    }

    @Override
    public SetDto toDto(final Set set, final boolean nested) {
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

        if (nested && set.getSetGroup() != null && set.getSetGroup().getId() != null) {
            final SetGroupDto setGroupDto = new SetGroupDto();
            setGroupDto.setId(set.getSetGroup().getId());
            setDto.setSetGroup(setGroupDto);
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
            final SetGroup setGroup = new SetGroup();
            setGroup.setId(setDto.getSetGroup().getId());
            set.setSetGroup(setGroup);
        }

        return set;
    }
}
