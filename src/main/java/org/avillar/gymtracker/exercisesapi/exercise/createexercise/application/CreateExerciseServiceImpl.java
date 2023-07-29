package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
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
import org.avillar.gymtracker.exercisesapi.exception.application.CreateExerciseException;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.mapper.CreateExerciseApplicationMapper;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseRequestApplication;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseRequestApplication.MuscleGroupExercises;
import org.avillar.gymtracker.exercisesapi.exercise.createexercise.application.model.CreateExerciseResponseApplication;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class CreateExerciseServiceImpl implements CreateExerciseService {

  private static final int FIRST = 0;

  private static final int MAX_TOTAL_WEIGHT = 1;

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
    exercise.setAccessType(AccessTypeEnum.PRIVATE);

    authExercisesService.checkAccess(exercise, AuthOperations.CREATE);

    final Set<UUID> muscleGroupIds =
        request.getMuscleGroupExercises().stream()
            .map(MuscleGroupExercises::getMuscleGroupId)
            .collect(Collectors.toSet());

    final CompletableFuture<List<Exercise>> exercisesByNameAndOwner =
        CompletableFuture.supplyAsync(() -> getExercisesByNameAndOwner(request.getName(), userId));
    final CompletableFuture<LoadType> loadType =
        CompletableFuture.supplyAsync(() -> getLoadType(request.getLoadTypeId()));
    final CompletableFuture<List<MuscleGroup>> muscleGroups =
        CompletableFuture.supplyAsync(() -> getMuscleGroups(muscleGroupIds));
    final CompletableFuture<Set<MuscleSubGroup>> muscleSubGroups =
        CompletableFuture.supplyAsync(
            () -> getMuscleSubGroups(new HashSet<>(request.getMuscleSubGroupsIds())));

    if (!CollectionUtils.isEmpty(exercisesByNameAndOwner.join())) {
      throw new CreateExerciseException("Already exists an exercise with this name for this user");
    }

    final List<UUID> missedMuscleGroupIds =
        getMissedMuscleGroups(muscleGroupIds, muscleSubGroups.join());
    if (!CollectionUtils.isEmpty(missedMuscleGroupIds)) {
      // The muscleSubGroup is not a sub entity of the muscleGroups specified
      throw new CreateExerciseException(
          "Missed muscleGroups for this muscleSubGroups: " + missedMuscleGroupIds);
    }

    exercise.setLoadType(loadType.join());
    exercise.setMuscleSubGroups(muscleSubGroups.join());

    final List<MuscleGroupExercise> muscleGroupExercises =
        createMuscleGroupExercises(exercise, muscleGroups.join(), request);

    if (getTotalWeight(muscleGroupExercises) > MAX_TOTAL_WEIGHT) {
      throw new CreateExerciseException("Excessive total muscle weight");
    }

    exerciseDao.save(exercise);
    muscleGroupExerciseDao.saveAll(muscleGroupExercises);

    return createExerciseApplicationMapper.map(exercise);
  }

  private List<Exercise> getExercisesByNameAndOwner(final String exerciseName, final UUID userId) {
    return exerciseDao.getByNameAndOwner(
        exerciseName, userId, AccessTypeEnum.PRIVATE, AccessTypeEnum.PUBLIC);
  }

  private LoadType getLoadType(final UUID loadTypeId) {
    return loadTypeDao
        .findById(loadTypeId)
        .orElseThrow(() -> new EntityNotFoundException(LoadType.class, loadTypeId));
  }

  private List<MuscleGroup> getMuscleGroups(final Set<UUID> muscleGroupsIds) {
    final List<MuscleGroup> muscleGroups =
        muscleGroupsIds.isEmpty()
            ? Collections.emptyList()
            : muscleGroupDao.findAllById(muscleGroupsIds);

    if (muscleGroups.size() != muscleGroupsIds.size()) {
      final List<UUID> foundedIds = muscleGroups.stream().map(BaseEntity::getId).toList();
      throw new EntityNotFoundException(
          MuscleGroup.class, getMissedIds(muscleGroupsIds, foundedIds).get(FIRST));
      // TODO Show all missed ids, not only the first one
    }

    return muscleGroups;
  }

  private Set<MuscleSubGroup> getMuscleSubGroups(final Set<UUID> muscleSubGroupsIds) {
    final List<MuscleSubGroup> muscleSubGroups =
        muscleSubGroupsIds.isEmpty()
            ? Collections.emptyList()
            : muscleSubGroupDao.findAllById(muscleSubGroupsIds);

    if (muscleSubGroups.size() != muscleSubGroupsIds.size()) {
      final List<UUID> foundedIds = muscleSubGroups.stream().map(BaseEntity::getId).toList();
      throw new EntityNotFoundException(
          MuscleGroup.class, getMissedIds(muscleSubGroupsIds, foundedIds).get(FIRST));
      // TODO Show all missed ids, not only the first one
    }

    return new HashSet<>(muscleSubGroups);
  }

  private List<UUID> getMissedMuscleGroups(
      final Collection<UUID> muscleGroupsIds, final Collection<MuscleSubGroup> muscleSubGroups) {
    final List<UUID> muscleGroupIdsNeeded =
        muscleSubGroups.stream()
            .map(muscleSubGroup -> muscleSubGroup.getMuscleGroup().getId())
            .toList();
    return getMissedIds(muscleGroupIdsNeeded, muscleGroupsIds);
  }

  private int getTotalWeight(List<MuscleGroupExercise> muscleGroupExercises) {
    return muscleGroupExercises.stream()
        .map(MuscleGroupExercise::getWeight)
        .mapToInt(Double::intValue)
        .sum();
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

  private List<UUID> getMissedIds(
      final Collection<UUID> totalIds, final Collection<UUID> foundedIds) {
    return totalIds.stream().filter(uuid -> !foundedIds.contains(uuid)).toList();
  }
}
