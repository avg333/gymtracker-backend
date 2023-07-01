package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.domain.LoadTypeDao;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.GetLoadTypeServiceImpl;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.mapper.GetAllLoadTypesServiceMapperImpl;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model.GetAllLoadTypesResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetLoadTypeServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetLoadTypeServiceImpl getLoadTypeService;

  @Mock private LoadTypeDao loadTypeDao;
  @Spy private GetAllLoadTypesServiceMapperImpl getAllLoadTypesServiceMapper;

  @Test
  void execute() {
    final List<LoadType> expected = easyRandom.objects(LoadType.class, 2).toList();

    when(loadTypeDao.findAll()).thenReturn(expected);

    final List<GetAllLoadTypesResponseApplication> result = getLoadTypeService.execute();
    assertEquals(expected.size(), result.size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getId(), result.get(i).getId());
      assertEquals(expected.get(i).getName(), result.get(i).getName());
      assertEquals(expected.get(i).getDescription(), result.get(i).getDescription());
    }
  }
}