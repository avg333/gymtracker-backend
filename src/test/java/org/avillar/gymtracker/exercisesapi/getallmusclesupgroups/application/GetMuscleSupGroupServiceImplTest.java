package org.avillar.gymtracker.exercisesapi.getallmusclesupgroups.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSupGroupDao;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.GetMuscleSupGroupServiceImpl;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.mapper.GetAllMuscleSupGroupsApplicationMapper;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.getallmusclesupgroups.application.model.GetAllMuscleSupGroupsResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class GetMuscleSupGroupServiceImplTest {

  private static final int LIST_SIZE = 5;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private GetMuscleSupGroupServiceImpl getGetMuscleSupGroupService;

  @Mock private MuscleSupGroupDao muscleSupGroupDao;

  @Spy
  private final GetAllMuscleSupGroupsApplicationMapper getMuscleSupGroupApplicationMapper =
      Mappers.getMapper(GetAllMuscleSupGroupsApplicationMapper.class);

  @Test
  void execute() {
    final List<MuscleSupGroup> expected =
        easyRandom.objects(MuscleSupGroup.class, LIST_SIZE).toList();
    expected.forEach(
        mg ->
            mg.setMuscleGroups(
                easyRandom.objects(MuscleGroup.class, LIST_SIZE).collect(Collectors.toSet())));
    expected.forEach(
        mg ->
            mg.getMuscleGroups()
                .forEach(
                    msg ->
                        msg.setMuscleSubGroups(
                            easyRandom
                                .objects(MuscleSubGroup.class, LIST_SIZE)
                                .collect(Collectors.toSet()))));

    when(muscleSupGroupDao.getAll()).thenReturn(expected);

    final List<GetAllMuscleSupGroupsResponseApplication> result =
        getGetMuscleSupGroupService.execute();
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
