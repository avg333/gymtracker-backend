package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastrucuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.GetLoadTypeService;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.application.model.GetAllLoadTypesResponseApplication;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.GetAllLoadTypesControllerImpl;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.mapper.GetAllLoadTypesControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes.infrastructure.model.GetAllLoadTypesResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class GetAllLoadTypesControllerImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetAllLoadTypesControllerImpl getAllLoadTypesControllerImpl;
  @Mock private GetLoadTypeService getLoadTypeService;
  @Spy private GetAllLoadTypesControllerMapperImpl getLoadTypesControllerMapper;

  @Test
  void get() {
    final List<GetAllLoadTypesResponseApplication> expected =
        easyRandom.objects(GetAllLoadTypesResponseApplication.class, 2).toList();

    when(getLoadTypeService.execute()).thenReturn(expected);

    final ResponseEntity<List<GetAllLoadTypesResponseInfrastructure>> result =
        getAllLoadTypesControllerImpl.execute();
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(expected.size(), result.getBody().size());
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i).getId(), result.getBody().get(i).getId());
      assertEquals(expected.get(i).getName(), result.getBody().get(i).getName());
      assertEquals(expected.get(i).getDescription(), result.getBody().get(i).getDescription());
    }
  }
}