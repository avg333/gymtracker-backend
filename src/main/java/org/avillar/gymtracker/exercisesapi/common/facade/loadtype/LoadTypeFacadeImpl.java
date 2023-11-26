package org.avillar.gymtracker.exercisesapi.common.facade.loadtype;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.LoadTypeDao;
import org.avillar.gymtracker.exercisesapi.common.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.common.facade.loadtype.mapper.LoadTypeFacadeMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LoadTypeFacadeImpl implements LoadTypeFacade {

  private final LoadTypeDao loadTypeDao;
  private final LoadTypeFacadeMapper loadTypeFacadeMapper;

  @Override
  public List<LoadType> getAllLoadTypes() {
    return loadTypeFacadeMapper.map(loadTypeDao.findAll());
  }
}
