package org.avillar.gymtracker.exercisesapi.getallmusclegroupsbymusclesupgroup.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.GetAllMuscleGroupsByMuscleSupGroupServiceImpl;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.mapper.GetAllMuscleGroupsByMuscleSupGroupServiceMapperImpl;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.model.GetAllMuscleGroupsByMuscleSupGroupResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllMuscleGroupsByMuscleSupGroupServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks
  private GetAllMuscleGroupsByMuscleSupGroupServiceImpl getAllMuscleGroupsByMuscleSupGroupService;

  @Mock private MuscleGroupDao muscleGroupDao;

  @Spy
  private GetAllMuscleGroupsByMuscleSupGroupServiceMapperImpl
      getAllMuscleGroupsByMuscleSupGroupServiceMapper;

  @Test
  void execute() {
    final UUID muscleSupGroupId = UUID.randomUUID();

    final List<MuscleGroup> expected = easyRandom.objects(MuscleGroup.class, 2).toList();
    expected.forEach(
        msg ->
            msg.setMuscleSubGroups(
                easyRandom.objects(MuscleSubGroup.class, 2).collect(Collectors.toSet())));

    when(muscleGroupDao.getALlMuscleGroupsByMuscleSupGroupId(muscleSupGroupId))
        .thenReturn(expected);

    final List<GetAllMuscleGroupsByMuscleSupGroupResponseApplication> result =
        getAllMuscleGroupsByMuscleSupGroupService.execute(muscleSupGroupId);
    assertEquals(expected.size(), result.size());
    for (int i = 0; i < expected.size(); i++) {
      final var mgExpected = expected.get(i);
      final var mgResult = result.get(i);
      assertEquals(mgExpected.getId(), mgResult.getId());
      assertEquals(mgExpected.getName(), mgResult.getName());
      assertEquals(mgExpected.getDescription(), mgResult.getDescription());
      assertEquals(mgExpected.getMuscleSubGroups().size(), mgResult.getMuscleSubGroups().size());

      for (int k = 0; k < mgExpected.getMuscleSubGroups().size(); k++) {
        final var mSubExpected = mgExpected.getMuscleSubGroups().stream().toList().get(k);
        final var mSubResult = mgResult.getMuscleSubGroups().get(k);
        assertEquals(mSubExpected.getId(), mSubResult.getId());
        assertEquals(mSubExpected.getName(), mSubResult.getName());
        assertEquals(mSubExpected.getDescription(), mSubResult.getDescription());
      }
    }
  }
}
