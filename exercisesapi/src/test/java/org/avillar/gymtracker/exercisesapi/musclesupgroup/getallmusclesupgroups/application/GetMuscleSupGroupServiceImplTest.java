package org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSupGroupDao;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.GetMuscleSupGroupServiceImpl;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.mapper.GetAllMuscleSupGroupsApplicationMapperImpl;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.model.GetAllMuscleSupGroupsResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetMuscleSupGroupServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetMuscleSupGroupServiceImpl getGetMuscleSupGroupService;

  @Mock private MuscleSupGroupDao muscleSupGroupDao;
  @Spy private GetAllMuscleSupGroupsApplicationMapperImpl getMuscleSupGroupApplicationMapper;

  @Test
  void execute() {
    final List<MuscleSupGroup> expected = easyRandom.objects(MuscleSupGroup.class, 2).toList();
    expected.forEach(
        mg ->
            mg.setMuscleGroups(
                easyRandom.objects(MuscleGroup.class, 2).collect(Collectors.toSet())));
    expected.forEach(
        mg ->
            mg.getMuscleGroups()
                .forEach(
                    msg ->
                        msg.setMuscleSubGroups(
                            easyRandom
                                .objects(MuscleSubGroup.class, 2)
                                .collect(Collectors.toSet()))));

    when(muscleSupGroupDao.getAll()).thenReturn(expected);

    final List<GetAllMuscleSupGroupsResponseApplication> result =
        getGetMuscleSupGroupService.execute();
    assertEquals(expected.size(), result.size());
    for (int i = 0; i < expected.size(); i++) {
      final var mSupGExpected = expected.get(i);
      final var mSupGResult = result.get(i);
      assertEquals(mSupGExpected.getId(), mSupGResult.getId());
      assertEquals(mSupGExpected.getName(), mSupGResult.getName());
      assertEquals(mSupGExpected.getDescription(), mSupGResult.getDescription());
      assertEquals(mSupGExpected.getMuscleGroups().size(), mSupGResult.getMuscleGroups().size());

      for (int j = 0; j < mSupGExpected.getMuscleGroups().size(); j++) {
        final var mgExpected = mSupGExpected.getMuscleGroups().stream().toList().get(j);
        final var mgResult = mSupGResult.getMuscleGroups().get(j);
        assertEquals(mgExpected.getId(), mgResult.getId());
        assertEquals(mgExpected.getName(), mgResult.getName());
        assertEquals(mgExpected.getDescription(), mgResult.getDescription());
        assertEquals(mgExpected.getMuscleSubGroups().size(), mgResult.getMuscleSubGroups().size());

        for (int k = 0; k < mSupGExpected.getMuscleGroups().size(); k++) {
          final var mSubExpected = mgExpected.getMuscleSubGroups().stream().toList().get(k);
          final var mSubResult = mgResult.getMuscleSubGroups().get(k);
          assertEquals(mSubExpected.getId(), mSubResult.getId());
          assertEquals(mSubExpected.getName(), mSubResult.getName());
          assertEquals(mSubExpected.getDescription(), mSubResult.getDescription());
        }
      }
    }
  }
}
