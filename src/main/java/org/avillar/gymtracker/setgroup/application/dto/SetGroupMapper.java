package org.avillar.gymtracker.setgroup.application.dto;

import org.avillar.gymtracker.setgroup.domain.SetGroup;

import java.util.Collection;
import java.util.List;

public interface SetGroupMapper {

    List<SetGroupDto> toDtos(Collection<SetGroup> setGroups, boolean nested);

    SetGroupDto toDto(SetGroup setGroup, boolean nested);

    SetGroup toEntity(SetGroupDto setGroupDto);

}
