package org.avillar.gymtracker.workoutapi.common.facade.setgroup;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.SetGroupDao;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetGroupNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutEntityFacadeMapper;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutFacadeMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SetGroupFacadeImpl implements SetGroupFacade {

  private final SetGroupDao setGroupDao;
  private final WorkoutFacadeMapper workoutFacadeMapper;
  private final WorkoutEntityFacadeMapper workoutEntityFacadeMapper;

  @Override
  public SetGroup getSetGroupFull(final UUID setGroupId) throws SetGroupNotFoundException {
    return workoutEntityFacadeMapper.map(
        setGroupDao.getSetGroupFullByIds(Set.of(setGroupId)).stream()
            .findAny()
            .orElseThrow(() -> new SetGroupNotFoundException(setGroupId)));
  }

  @Override
  public List<SetGroup> getSetGroupFullByIds(final Collection<UUID> setGroupIds) {
    return workoutEntityFacadeMapper.mapSetGroups(
        setGroupDao.getSetGroupFullByIds(new HashSet<>(setGroupIds)));
  }

  @Override
  public SetGroup getSetGroupWithWorkout(final UUID setGroupId) throws SetGroupNotFoundException {
    return workoutEntityFacadeMapper.map(
        setGroupDao
            .getSetGroupWithWorkoutById(setGroupId)
            .orElseThrow(() -> new SetGroupNotFoundException(setGroupId)));
  }

  @Override
  public List<SetGroup> getSetGroupsByWorkoutId(final UUID workoutId) {
    return workoutEntityFacadeMapper.mapSetGroups(setGroupDao.getSetGroupsByWorkoutId(workoutId));
  }

  @Override
  public List<SetGroup> getSetGroupsFullByUserIdAndExerciseId(
      final UUID userId, final UUID exerciseId) {
    return workoutEntityFacadeMapper.mapSetGroups(
        setGroupDao.getSetGroupsFullByUserIdAndExerciseId(userId, exerciseId));
  }

  @Override
  public SetGroup saveSetGroup(final SetGroup setGroup) {
    return workoutEntityFacadeMapper.map(setGroupDao.save(workoutFacadeMapper.map(setGroup)));
  }

  @Override
  public List<SetGroup> saveSetGroups(final List<SetGroup> setGroups) {
    return workoutEntityFacadeMapper.mapSetGroups(
        setGroupDao.saveAll(workoutFacadeMapper.mapSetGroups(setGroups)));
  }

  @Override
  public void deleteSetGroup(final UUID setGroupId) {
    setGroupDao.deleteById(setGroupId);
  }
}
