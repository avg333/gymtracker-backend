package org.avillar.gymtracker.exercisesapi.common.facade.loadtype.mapper;

import java.util.Collections;
import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.LoadTypeEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

@Component
public class LoadTypeFacadeMapperImpl implements LoadTypeFacadeMapper {

  @Override
  public List<LoadType> map(final List<LoadTypeEntity> loadTypeEntities) {
    if (loadTypeEntities == null || !Hibernate.isInitialized(loadTypeEntities)) {
      return Collections.emptyList();
    }

    return loadTypeEntities.stream().map(this::map).toList();
  }

  private LoadType map(final LoadTypeEntity loadTypeEntity) {
    if (loadTypeEntity == null || !Hibernate.isInitialized(loadTypeEntity)) {
      return null;
    }

    return LoadType.builder()
        .id(loadTypeEntity.getId())
        .name(loadTypeEntity.getName())
        .description(loadTypeEntity.getDescription())
        .build();
  }
}
