package org.avillar.gymtracker.exercisesapi.loadtype.application.get;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.mapper.GetLoadTypeServiceMapper;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.model.GetLoadTypesApplicationResponse;
import org.avillar.gymtracker.exercisesapi.loadtype.domain.LoadTypeDao;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetLoadTypeServiceImpl implements GetLoadTypeService {

  private final LoadTypeDao loadTypeDao;
  private final GetLoadTypeServiceMapper getLoadTypeServiceMapper;

  @Override
  public List<GetLoadTypesApplicationResponse> execute() {
    return getLoadTypeServiceMapper.map(loadTypeDao.findAll());
  }
}
