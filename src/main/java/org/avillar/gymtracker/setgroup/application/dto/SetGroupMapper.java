package org.avillar.gymtracker.setgroup.application.dto;

import org.avillar.gymtracker.setgroup.domain.SetGroup;

import java.util.Collection;
import java.util.List;

public interface SetGroupMapper {

    List<SetGroupDto> toDtos(Collection<SetGroup> setGroups, int depth);

    List<SetGroup> toEntities(Collection<SetGroupDto> setGroupDtos);

    SetGroupDto toDto(SetGroup setGroup, int depth);

    SetGroup toEntity(SetGroupDto setGroupDto);

}
