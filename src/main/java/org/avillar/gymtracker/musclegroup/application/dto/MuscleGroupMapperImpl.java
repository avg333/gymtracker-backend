package org.avillar.gymtracker.musclegroup.application.dto;

import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleSubGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleSupGroup;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class MuscleGroupMapperImpl implements MuscleGroupMapper {

    @Override
    public <T> List<T> toDtos(Collection<? extends BaseEntity> objects, boolean nested) {
        if (CollectionUtils.isEmpty(objects)) {
            return Collections.emptyList();
        }

        final Object firstObject = objects.iterator().next();
        if (firstObject instanceof MuscleGroup) {
            return (List<T>) objects.stream().map(object -> this.toDto((MuscleGroup) object, nested)).toList();
        } else if (firstObject instanceof MuscleSupGroup) {
            return (List<T>) objects.stream().map(object -> this.toDto((MuscleSupGroup) object, nested)).toList();
        } else if (firstObject instanceof MuscleSubGroup) {
            return (List<T>) objects.stream().map(object -> this.toDto((MuscleSubGroup) object, nested)).toList();
        }

        return Collections.emptyList();
    }

    @Override
    public MuscleGroupDto toDto(MuscleGroup muscleGroup, boolean nested) {
        if (muscleGroup == null) {
            return null;
        }

        MuscleGroupDto muscleGroupDto = new MuscleGroupDto();

        muscleGroupDto.setId(muscleGroup.getId());
        muscleGroupDto.setName(muscleGroup.getName());
        muscleGroupDto.setDescription(muscleGroup.getDescription());
        muscleGroupDto.setMuscleSupGroups(nested
                ? this.toDtos(muscleGroup.getMuscleSupGroups(), false)
                : Collections.emptyList());
        muscleGroupDto.setMuscleSubGroups(nested
                ? this.toDtos(muscleGroup.getMuscleSubGroups(), false)
                : Collections.emptyList());

        return muscleGroupDto;
    }

    @Override
    public MuscleSupGroupDto toDto(MuscleSupGroup muscleSupGroup, boolean nested) {
        if (muscleSupGroup == null) {
            return null;
        }

        MuscleSupGroupDto muscleSupGroupDto = new MuscleSupGroupDto();

        muscleSupGroupDto.setId(muscleSupGroup.getId());
        muscleSupGroupDto.setName(muscleSupGroup.getName());
        muscleSupGroupDto.setDescription(muscleSupGroup.getDescription());
        muscleSupGroupDto.setMuscleGroups(nested
                ? this.toDtos(muscleSupGroup.getMuscleGroups(), false)
                : Collections.emptyList());

        return muscleSupGroupDto;
    }

    @Override
    public MuscleSubGroupDto toDto(MuscleSubGroup muscleSubGroup, boolean nested) {
        if (muscleSubGroup == null) {
            return null;
        }

        MuscleSubGroupDto muscleSubGroupDto = new MuscleSubGroupDto();

        muscleSubGroupDto.setId(muscleSubGroup.getId());
        muscleSubGroupDto.setName(muscleSubGroup.getName());
        muscleSubGroupDto.setDescription(muscleSubGroup.getDescription());
        muscleSubGroupDto.setMuscleGroup(nested
                ? this.toDto(muscleSubGroup.getMuscleGroup(), false)
                : null);

        return muscleSubGroupDto;
    }

}
