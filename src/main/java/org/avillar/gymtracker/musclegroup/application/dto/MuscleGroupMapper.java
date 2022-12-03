package org.avillar.gymtracker.musclegroup.application.dto;

import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleSubGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleSupGroup;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface MuscleGroupMapper {


    <T> List<T> toDtos(Collection<? extends BaseEntity> objects, boolean nested);
    <T> Set<T> toEntities(Collection<?> objects);

    MuscleGroupDto toDto(MuscleGroup muscleGroup, boolean nested);

    MuscleSupGroupDto toDto(MuscleSupGroup muscleSupGroup, boolean nested);

    MuscleSubGroupDto toDto(MuscleSubGroup muscleSubGroup, boolean nested);

    MuscleGroup toEntity(MuscleGroupDto muscleGroupDto);

    MuscleSupGroup toEntity(MuscleSupGroupDto muscleSupGroupDto);

    MuscleSubGroup toEntity(MuscleSubGroupDto muscleSubGroupDto);

}
