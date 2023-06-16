package org.avillar.gymtracker.exercisesapi.loadtype.application.get;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.mapper.GetLoadTypeServiceMapper;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.model.GetLoadTypeResponse;
import org.avillar.gymtracker.exercisesapi.loadtype.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.loadtype.domain.LoadTypeDao;
import org.avillar.gymtracker.workoutapi.errors.application.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetLoadTypeServiceImpl implements GetLoadTypeService {

  private final LoadTypeDao loadTypeDao;
  private final GetLoadTypeServiceMapper getLoadTypeServiceMapper;

  @Override
  public GetLoadTypeResponse getLoadType(final UUID loadTypeId) {
    return getLoadTypeServiceMapper.getResponse(
        loadTypeDao
            .findById(loadTypeId)
            .orElseThrow(() -> new EntityNotFoundException(LoadType.class, loadTypeId)));
  }

  @Override
  public List<GetLoadTypeResponse> getAllLoadTypes() {
    return getLoadTypeServiceMapper.getResponse(loadTypeDao.findAll());
  }
}
