package org.avillar.gymtracker.exercisesapi.common.facade.loadtype.mapper;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.LoadTypeEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;

public interface LoadTypeFacadeMapper {

  List<LoadType> map(List<LoadTypeEntity> loadTypeEntities);
}
