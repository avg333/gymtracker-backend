package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.domain.LoadTypeDao;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.mapper.GetAllLoadTypesServiceMapper;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model.GetAllLoadTypesResponseApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

@Service
@EnableCaching
@RequiredArgsConstructor
public class GetLoadTypeServiceImpl implements GetLoadTypeService {

  private final LoadTypeDao loadTypeDao;
  private final GetAllLoadTypesServiceMapper getAllLoadTypesServiceMapper;

  @Cacheable(cacheNames = "loadTypes")
  @Override
  public List<GetAllLoadTypesResponseApplication> execute() {
    return getAllLoadTypesServiceMapper.map(loadTypeDao.findAll());
  }
}
