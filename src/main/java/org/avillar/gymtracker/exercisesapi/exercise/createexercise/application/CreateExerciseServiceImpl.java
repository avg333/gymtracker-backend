package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.base.domain.BaseEntity;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.auth.application.AuthExercisesService;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.domain.LoadTypeDao;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupExerciseDao;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroupDao;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.mapper.CreateExerciseApplicationMapper;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseRequestApplication.MuscleGroupExercises;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseResponseApplication;
import org.springframework.stereotype.Service;

// TODO Finish this
@Service
@RequiredArgsConstructor
public class CreateExerciseServiceImpl implements CreateExerciseService {

  private final ExerciseDao exerciseDao;
  private final LoadTypeDao loadTypeDao;
  private final MuscleSubGroupDao muscleSubGroupDao;
  private final MuscleGroupDao muscleGroupDao;
  private final MuscleGroupExerciseDao muscleGroupExerciseDao;
  private final AuthExercisesService authExercisesService;
  private final CreateExerciseApplicationMapper createExerciseApplicationMapper;

  @Transactional
  @Override
  public CreateExerciseResponseApplication execute(
      final UUID userId, final CreateExerciseRequestApplication request)
      throws EntityNotFoundException, IllegalAccessException {

    final Exercise exercise = createExerciseApplicationMapper.map(request);
    exercise.setOwner(userId);
    exercise.setAccessType(AccessTypeEnum.PUBLIC);

    authExercisesService.checkAccess(exercise, AuthOperations.CREATE);

    exercise.setLoadType(getLoadType(request.getLoadTypeId()));

    final List<MuscleGroup> muscleGroups =
        getMuscleGroups(
            request.getMuscleGroupExercises().stream()
                .map(MuscleGroupExercises::getMuscleGroupId)
                .toList());

    exercise.setMuscleSubGroups(
        getMuscleSubGroups(
            request.getMuscleSubGroupsIds(),
            muscleGroups.stream().map(BaseEntity::getId).toList()));

    exerciseDao.save(exercise);
    muscleGroupExerciseDao.saveAll(createMuscleGroupExercises(exercise, muscleGroups, request));

    return createExerciseApplicationMapper.map(exercise);
  }

  private LoadType getLoadType(final UUID loadTypeId) {
    return loadTypeDao
        .findById(loadTypeId)
        .orElseThrow(() -> new EntityNotFoundException(LoadType.class, loadTypeId));
  }

  private List<MuscleGroup> getMuscleGroups(final List<UUID> muscleGroupsIds) {
    final List<MuscleGroup> muscleGroups =
        muscleGroupsIds.isEmpty()
            ? Collections.emptyList()
            : muscleGroupDao.findAllById(muscleGroupsIds);

    if (muscleGroups.size() != muscleGroupsIds.size()) {
      throw new EntityNotFoundException(MuscleGroup.class, UUID.randomUUID());
    }

    return muscleGroups;
  }

  private Set<MuscleSubGroup> getMuscleSubGroups(
      final List<UUID> muscleSubGroupsIds, final List<UUID> muscleGroupsIds) {
    final List<MuscleSubGroup> muscleSubGroups =
        muscleSubGroupsIds.isEmpty()
            ? Collections.emptyList()
            : muscleSubGroupDao.findAllById(muscleSubGroupsIds);

    if (muscleSubGroups.size() != muscleSubGroupsIds.size()) {
      throw new EntityNotFoundException(MuscleGroup.class, UUID.randomUUID());
    }

    for (final MuscleSubGroup msg : muscleSubGroups) {
      if (!muscleGroupsIds.contains(msg.getMuscleGroup().getId())) {
        throw new EntityNotFoundException(MuscleGroup.class, msg.getMuscleGroup().getId());
      }
    }

    return new HashSet<>(muscleSubGroups);
  }

  private List<MuscleGroupExercise> createMuscleGroupExercises(
      final Exercise exercise,
      final List<MuscleGroup> muscleGroups,
      final CreateExerciseRequestApplication request) {
    final List<MuscleGroupExercise> muscleGroupExercises =
        new ArrayList<>(request.getMuscleGroupExercises().size());

    for (MuscleGroupExercises mge : request.getMuscleGroupExercises()) {
      final MuscleGroup mg =
          muscleGroups.stream()
              .filter(muscleGroup -> muscleGroup.getId().equals(mge.getMuscleGroupId()))
              .findAny()
              .orElseThrow(
                  () -> new EntityNotFoundException(MuscleGroup.class, mge.getMuscleGroupId()));
      muscleGroupExercises.add(new MuscleGroupExercise(mge.getWeight(), mg, exercise));
    }

    return muscleGroupExercises;
  }
}
