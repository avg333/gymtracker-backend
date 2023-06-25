package org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.mapper.GetMuscleSupGroupApplicationMapperImpl;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get.model.GetMuscleSupGroupsApplicationResponse;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.domain.MuscleSupGroupDao;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetMuscleSupGroupServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();
  private GetMuscleSupGroupService getGetMuscleSupGroupService;
  @Mock private MuscleSupGroupDao muscleSupGroupDao;
  @Spy private GetMuscleSupGroupApplicationMapperImpl getMuscleSupGroupApplicationMapper;

  @BeforeEach
  void beforeEach() {
    getGetMuscleSupGroupService =
        new GetMuscleSupGroupServiceImpl(muscleSupGroupDao, getMuscleSupGroupApplicationMapper);
  }

  @Test
  void execute() {
    final List<MuscleSupGroup> muscleSupGroups =
        easyRandom.objects(MuscleSupGroup.class, 2).toList();

    when(muscleSupGroupDao.getAll()).thenReturn(muscleSupGroups);

    final List<GetMuscleSupGroupsApplicationResponse> getMuscleSupGroupsApplicationResponses =
        getGetMuscleSupGroupService.execute();
    assertEquals(muscleSupGroups.size(), getMuscleSupGroupsApplicationResponses.size());
    assertEquals(
        muscleSupGroups.get(0).getId(), getMuscleSupGroupsApplicationResponses.get(0).getId());
    assertEquals(
        muscleSupGroups.get(0).getName(), getMuscleSupGroupsApplicationResponses.get(0).getName());
    assertEquals(
        muscleSupGroups.get(0).getDescription(),
        getMuscleSupGroupsApplicationResponses.get(0).getDescription());
    assertEquals(
        muscleSupGroups.get(0).getMuscleGroups().size(),
        getMuscleSupGroupsApplicationResponses.get(0).getMuscleGroups().size());
  }
}
