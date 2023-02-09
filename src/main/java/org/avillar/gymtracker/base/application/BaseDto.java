package org.avillar.gymtracker.base.application;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
public abstract class BaseDto implements Serializable {

    private Long id;

    protected BaseDto(Long id) {
        this.id = id;
    }

    public static <T extends BaseDto> boolean exists(T baseEntity) {
        return baseEntity != null && baseEntity.getId() != null && baseEntity.getId() > 0L;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (null == o || getClass() != o.getClass()) return false;
        final BaseDto baseEntity = (BaseDto) o;
        return id != null && baseEntity.id != null && id.equals(baseEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
