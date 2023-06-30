package org.avillar.gymtracker.exercisesapi.musclesupgroup.application.get;

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
    assertEquals(expected.get(0).getId(), result.get(0).getId());
    assertEquals(expected.get(0).getName(), result.get(0).getName());
    assertEquals(expected.get(0).getDescription(), result.get(0).getDescription());
    assertEquals(expected.get(0).getMuscleGroups().size(), result.get(0).getMuscleGroups().size());
    final List<MuscleGroup> mgExpected = expected.get(0).getMuscleGroups().stream().toList();
    final List<GetAllMuscleSupGroupsResponseApplication.MuscleGroup> mgResult =
        result.get(0).getMuscleGroups();
    assertEquals(mgExpected.size(), mgResult.size());
    assertEquals(mgExpected.get(0).getId(), mgResult.get(0).getId());
    assertEquals(mgExpected.get(0).getName(), mgResult.get(0).getName());
    assertEquals(mgExpected.get(0).getDescription(), mgResult.get(0).getDescription());
    final List<MuscleSubGroup> msgExpected =
        mgExpected.get(0).getMuscleSubGroups().stream().toList();
    final List<GetAllMuscleSupGroupsResponseApplication.MuscleGroup.MuscleSubGroup> msgResult =
        mgResult.get(0).getMuscleSubGroups();
    assertEquals(msgExpected.size(), msgResult.size());
    assertEquals(msgExpected.get(0).getId(), msgResult.get(0).getId());
    assertEquals(msgExpected.get(0).getName(), msgResult.get(0).getName());
    assertEquals(msgExpected.get(0).getDescription(), msgResult.get(0).getDescription());
  }
}
