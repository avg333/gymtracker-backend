package org.avillar.gymtracker.exercisesapi.common.facade.exerciseuses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.ExerciseUsesDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseUsesEntity;
import org.avillar.gymtracker.exercisesapi.common.domain.ExerciseUses;
import org.avillar.gymtracker.exercisesapi.common.facade.exerciseuses.mapper.ExerciseUsesFacadeMapper;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class ExerciseUsesFacadeImplTest {

  @InjectMocks private ExerciseUsesFacadeImpl exerciseUsesFacadeImpl;

  @Mock private ExerciseUsesDao exerciseUsesDao;
  @Mock private ExerciseUsesFacadeMapper exerciseUsesFacadeMapper;

  @Test
  void shouldGetExerciseUsesByExerciseIdAndUserIdSuccessfully() {
    final List<UUID> exerciseIds = Instancio.createList(UUID.class);
    final UUID userId = Instancio.create(UUID.class);
    final List<ExerciseUsesEntity> daoResponse = Instancio.createList(ExerciseUsesEntity.class);
    final List<ExerciseUses> mapperResponse = Instancio.createList(ExerciseUses.class);

    when(exerciseUsesDao.getExerciseUsesByExerciseIdAndUserId(exerciseIds, userId))
        .thenReturn(daoResponse);
    when(exerciseUsesFacadeMapper.mapExerciseUsesEntityList(daoResponse))
        .thenReturn(mapperResponse);

    assertThat(exerciseUsesFacadeImpl.getExerciseUsesByExerciseIdAndUserId(exerciseIds, userId))
        .isEqualTo(mapperResponse);
  }

  @Test
  void shouldSaveAllExerciseUsesSuccessfully() {
    final List<ExerciseUses> exerciseUsesBeforeSave = Instancio.createList(ExerciseUses.class);
    final List<ExerciseUsesEntity> exerciseUsesEntityBeforeSave =
        Instancio.createList(ExerciseUsesEntity.class);
    final List<ExerciseUsesEntity> exerciseUsesEntityAfterSave =
        Instancio.createList(ExerciseUsesEntity.class);
    final List<ExerciseUses> exerciseUsesAfterSave = Instancio.createList(ExerciseUses.class);

    when(exerciseUsesFacadeMapper.mapExerciseUsesList(exerciseUsesBeforeSave))
        .thenReturn(exerciseUsesEntityBeforeSave);
    when(exerciseUsesDao.saveAll(exerciseUsesEntityBeforeSave))
        .thenReturn(exerciseUsesEntityAfterSave);
    when(exerciseUsesFacadeMapper.mapExerciseUsesEntityList(exerciseUsesEntityAfterSave))
        .thenReturn(exerciseUsesAfterSave);

    assertThat(exerciseUsesFacadeImpl.saveAllExerciseUses(exerciseUsesBeforeSave))
        .isEqualTo(exerciseUsesAfterSave);
  }
}
