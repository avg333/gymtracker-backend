package org.avillar.gymtracker.exercisesapi.getallmusclegroupsbymusclesupgroup.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.GetAllMuscleGroupsByMuscleSupGroupServiceImpl;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.mapper.GetAllMuscleGroupsByMuscleSupGroupServiceMapper;
import org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup.application.model.GetAllMuscleGroupsByMuscleSupGroupResponseApplication;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetAllMuscleGroupsByMuscleSupGroupServiceImplTest {

  private static final int LIST_SIZE = 4;

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks
  private GetAllMuscleGroupsByMuscleSupGroupServiceImpl getAllMuscleGroupsByMuscleSupGroupService;

  @Mock private MuscleGroupDao muscleGroupDao;

  @Spy
  private final GetAllMuscleGroupsByMuscleSupGroupServiceMapper
      getAllMuscleGroupsByMuscleSupGroupServiceMapper =
          Mappers.getMapper(GetAllMuscleGroupsByMuscleSupGroupServiceMapper.class);

  @Test
  void execute() {
    final UUID muscleSupGroupId = UUID.randomUUID();

    final List<MuscleGroup> expected = easyRandom.objects(MuscleGroup.class, LIST_SIZE).toList();
    expected.forEach(
        msg ->
            msg.setMuscleSubGroups(
                easyRandom.objects(MuscleSubGroup.class, LIST_SIZE).collect(Collectors.toSet())));

    when(muscleGroupDao.getALlMuscleGroupsByMuscleSupGroupId(muscleSupGroupId))
        .thenReturn(expected);

    final List<GetAllMuscleGroupsByMuscleSupGroupResponseApplication> result =
        getAllMuscleGroupsByMuscleSupGroupService.execute(muscleSupGroupId);
    assertThat(result).hasSameSizeAs(expected);
    assertThat(result).usingRecursiveComparison().isEqualTo(expected);
  }
}
