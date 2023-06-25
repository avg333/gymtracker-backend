package org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.GetLoadTypeService;
import org.avillar.gymtracker.exercisesapi.loadtype.application.get.model.GetLoadTypesApplicationResponse;
import org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.mapper.GetLoadTypesControllerMapperImpl;
import org.avillar.gymtracker.exercisesapi.loadtype.infrastrucuture.get.model.GetLoadTypesResponseInfrastructure;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetLoadTypeControllerTest {

  private final EasyRandom easyRandom = new EasyRandom();
  private GetLoadTypeController getLoadTypeController;
  @Mock private GetLoadTypeService getLoadTypeService;
  @Spy private GetLoadTypesControllerMapperImpl getLoadTypesControllerMapper;

  @BeforeEach
  void beforeEach() {
    getLoadTypeController =
        new GetLoadTypeController(getLoadTypeService, getLoadTypesControllerMapper);
  }

  @Test
  void get() {
    final List<GetLoadTypesApplicationResponse> getLoadTypesApplicationResponses =
        easyRandom.objects(GetLoadTypesApplicationResponse.class, 2).toList();

    when(getLoadTypeService.execute()).thenReturn(getLoadTypesApplicationResponses);

    final List<GetLoadTypesResponseInfrastructure> getLoadTypesResponseInfrastructures =
        getLoadTypeController.get().getBody();
    assertEquals(
        getLoadTypesApplicationResponses.size(), getLoadTypesResponseInfrastructures.size());
    assertEquals(
        getLoadTypesApplicationResponses.get(0).getId(),
        getLoadTypesResponseInfrastructures.get(0).getId());
    assertEquals(
        getLoadTypesApplicationResponses.get(0).getName(),
        getLoadTypesResponseInfrastructures.get(0).getName());
    assertEquals(
        getLoadTypesApplicationResponses.get(0).getDescription(),
        getLoadTypesResponseInfrastructures.get(0).getDescription());
  }
}
