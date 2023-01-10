package org.avillar.gymtracker.base.application;

import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class BaseMapper {

    public List<?> toDtos(Collection<?> entities, final boolean nested) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().map(setGroup -> this.toDto(setGroup, nested)).toList();
    }

    public List<?> toEntities(Collection<?> dtos) {
        if (CollectionUtils.isEmpty(dtos)) {
            return Collections.emptyList();
        }

        return dtos.stream().map(this::toEntity).toList();
    }

    public abstract <T, I> T toDto(I entity, boolean nested);

    public abstract <I, T> I toEntity(T dto);

}
