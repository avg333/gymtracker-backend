package org.avillar.gymtracker.errors.application;

import jakarta.persistence.PersistenceException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.avillar.gymtracker.base.domain.BaseEntity;
import org.springframework.util.StringUtils;

@Data
@EqualsAndHashCode(callSuper = false)
public class EntityNotFoundException extends PersistenceException {

    private String className;
    private Long id;

    public <T extends BaseEntity> EntityNotFoundException(Class<T> entityClass, String searchParamType, String searchParamsValue) {
        super(EntityNotFoundException.generateMessage(entityClass.getSimpleName(), searchParamType, searchParamsValue));
    }

    public <T extends BaseEntity> EntityNotFoundException(Class<T> entityClass, Long id) {
        super(EntityNotFoundException.generateMessage(entityClass.getSimpleName(), "id", id.toString()));
        this.className = entityClass.getSimpleName();
        this.id = id;
    }

    private static String generateMessage(String className, String searchParamType, String searchParamsValue) {
        return StringUtils.capitalize(className) +
                " was not found for parameters " +
                searchParamType + " = " + searchParamsValue;
    }
}
