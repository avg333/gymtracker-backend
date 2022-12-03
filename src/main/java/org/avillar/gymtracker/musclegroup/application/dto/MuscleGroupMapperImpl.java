package org.avillar.gymtracker.musclegroup.application.dto;

import org.avillar.gymtracker.base.domain.BaseEntity;
import org.avillar.gymtracker.musclegroup.domain.MuscleGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleSubGroup;
import org.avillar.gymtracker.musclegroup.domain.MuscleSupGroup;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MuscleGroupMapperImpl implements MuscleGroupMapper {

    @Override
    public <T> List<T> toDtos(final Collection<? extends BaseEntity> objects, final boolean nested) {
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
    public <T> Set<T> toEntities(Collection<?> objects) {
        if (CollectionUtils.isEmpty(objects)) {
            return new HashSet<>();
        }

        final Object firstObject = objects.iterator().next();
        if (firstObject instanceof MuscleGroupDto) {
            return (Set<T>) objects.stream().map(object -> this.toEntity((MuscleGroupDto) object)).collect(Collectors.toSet());
        } else if (firstObject instanceof MuscleSupGroupDto) {
            return (Set<T>) objects.stream().map(object -> this.toEntity((MuscleSupGroupDto) object)).collect(Collectors.toSet());
        } else if (firstObject instanceof MuscleSubGroupDto) {
            return (Set<T>) objects.stream().map(object -> this.toEntity((MuscleSubGroupDto) object)).collect(Collectors.toSet());
        }

        return new HashSet<>();
    }

    @Override
    public MuscleGroupDto toDto(final MuscleGroup muscleGroup, final boolean nested) {
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
    public MuscleSupGroupDto toDto(final MuscleSupGroup muscleSupGroup, final boolean nested) {
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
    public MuscleSubGroupDto toDto(final MuscleSubGroup muscleSubGroup, final boolean nested) {
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

    @Override
    public MuscleGroup toEntity(MuscleGroupDto muscleGroupDto) {
        if (muscleGroupDto == null) {
            return null;
        }

        final MuscleGroup muscleGroup = new MuscleGroup();
        muscleGroup.setId(muscleGroupDto.getId());
        muscleGroup.setName(muscleGroupDto.getName());
        muscleGroup.setDescription(muscleGroupDto.getDescription());
        muscleGroup.setMuscleSupGroups(this.toEntities(muscleGroupDto.getMuscleSupGroups()));
        muscleGroup.setMuscleSubGroups(this.toEntities(muscleGroupDto.getMuscleSubGroups()));

        return muscleGroup;
    }

    @Override
    public MuscleSupGroup toEntity(MuscleSupGroupDto muscleSupGroupDto) {
        if (muscleSupGroupDto == null) {
            return null;
        }

        MuscleSupGroup muscleSupGroup = new MuscleSupGroup();

        muscleSupGroup.setId(muscleSupGroupDto.getId());
        muscleSupGroup.setName(muscleSupGroupDto.getName());
        muscleSupGroup.setDescription(muscleSupGroupDto.getDescription());
        muscleSupGroup.setMuscleGroups(this.toEntities(muscleSupGroupDto.getMuscleGroups()));

        return muscleSupGroup;
    }

    @Override
    public MuscleSubGroup toEntity(MuscleSubGroupDto muscleSubGroupDto) {
        if (muscleSubGroupDto == null) {
            return null;
        }

        MuscleSubGroup muscleSubGroup = new MuscleSubGroup();

        muscleSubGroup.setId(muscleSubGroupDto.getId());
        muscleSubGroup.setName(muscleSubGroupDto.getName());
        muscleSubGroup.setDescription(muscleSubGroupDto.getDescription());
        muscleSubGroup.setMuscleGroup(this.toEntity(muscleSubGroupDto.getMuscleGroup()));

        return muscleSubGroup;
    }

}
