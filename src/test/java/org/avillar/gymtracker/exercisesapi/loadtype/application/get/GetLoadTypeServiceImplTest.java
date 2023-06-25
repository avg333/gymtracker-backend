package org.avillar.gymtracker.exercisesapi.loadtype.application.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.mapper.GetLoadTypeServiceMapperImpl;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.model.GetLoadTypesApplicationResponse;
import org.avillar.gymtracker.exercisesapi.loadtype.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.loadtype.domain.LoadTypeDao;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetLoadTypeServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();
  private GetLoadTypeService getLoadTypeService;
  @Mock private LoadTypeDao loadTypeDao;
  @Spy private GetLoadTypeServiceMapperImpl getLoadTypeServiceMapper;

  @BeforeEach
  void beforeEach() {
    getLoadTypeService = new GetLoadTypeServiceImpl(loadTypeDao, getLoadTypeServiceMapper);
  }

  @Test
  void execute() {
    final List<LoadType> loadTypes = easyRandom.objects(LoadType.class, 2).toList();

    when(loadTypeDao.findAll()).thenReturn(loadTypes);

    final List<GetLoadTypesApplicationResponse> getLoadTypesApplicationResponses =
        getLoadTypeService.execute();
    assertEquals(loadTypes.size(), getLoadTypesApplicationResponses.size());
    assertEquals(loadTypes.get(0).getId(), getLoadTypesApplicationResponses.get(0).getId());
    assertEquals(loadTypes.get(0).getName(), getLoadTypesApplicationResponses.get(0).getName());
    assertEquals(
        loadTypes.get(0).getDescription(),
        getLoadTypesApplicationResponses.get(0).getDescription());
  }
}
