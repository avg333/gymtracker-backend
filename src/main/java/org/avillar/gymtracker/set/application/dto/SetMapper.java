package org.avillar.gymtracker.set.application.dto;

import org.avillar.gymtracker.set.domain.Set;

import java.util.Collection;
import java.util.List;

public interface SetMapper {

    List<SetDto> toDtos(Collection<Set> sets, int depth);

    List<Set> toEntities(Collection<SetDto> setDtos);

    SetDto toDto(Set set, int depth);

    Set toEntity(SetDto setDto);

}
