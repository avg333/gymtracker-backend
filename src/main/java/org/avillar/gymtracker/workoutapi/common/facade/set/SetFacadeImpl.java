package org.avillar.gymtracker.workoutapi.common.facade.set;

import java.sql.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.SetDao;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.exception.application.SetNotFoundException;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutEntityFacadeMapper;
import org.avillar.gymtracker.workoutapi.common.facade.mapper.WorkoutFacadeMapper;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SetFacadeImpl implements SetFacade {

  private final SetDao setDao;
  private final WorkoutFacadeMapper workoutFacadeMapper;
  private final WorkoutEntityFacadeMapper workoutEntityFacadeMapper;

  @Override
  public Set getSetFull(final UUID setId) throws SetNotFoundException {
    return workoutEntityFacadeMapper.map(
        setDao.getSetFullById(setId).orElseThrow(() -> new SetNotFoundException(setId)));
  }

  @Override
  public List<Set> getSetsBySetGroupId(final UUID setGroupId) {
    return workoutEntityFacadeMapper.mapSets(setDao.getSetsBySetGroupId(setGroupId));
  }

  @Override
  public Set getSetGroupExerciseHistory(final SetGroup setGroup) {
    return workoutEntityFacadeMapper.map(
        setDao
            .findLastSetForExerciseAndUserAux(
                setGroup.getWorkout().getUserId(),
                setGroup.getExerciseId(),
                Date.valueOf(setGroup.getWorkout().getDate()))
            .stream()
            .findAny()
            .orElse(null)); // TODO Improve this exception
  }

  @Override
  public Set saveSet(final Set set) {
    return workoutEntityFacadeMapper.map(setDao.save(workoutFacadeMapper.map(set)));
  }

  @Override
  public List<Set> saveSets(final List<Set> sets) {
    return workoutEntityFacadeMapper.mapSets(setDao.saveAll(workoutFacadeMapper.mapSets(sets)));
  }

  @Override
  public void deleteSet(final UUID setId) {
    setDao.deleteById(setId);
  }
}
