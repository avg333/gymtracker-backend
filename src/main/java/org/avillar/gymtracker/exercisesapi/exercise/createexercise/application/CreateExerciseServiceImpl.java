package org.avillar.gymtracker.exercisesapi.exercise.createexercise.application;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.LoadTypeDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleGroupExerciseDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleSubGroupDao;
import org.avillar.gymtracker.exercisesapi.common.auth.application.AuthExercisesService;
import org.avillar.gymtracker.workoutapi.workout.getworkoutdetails.infrastructure.model.GetWorkoutDetailsResponseDto.SetGroup.Exercise;
import org.springframework.stereotype.Service;

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

  @Override
  public Exercise execute(final UUID userId, final Exercise exercise)
      throws EntityNotFoundException, IllegalAccessException {
    return null;
  }
}
/*
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
        request.getMuscleGroups().stream()
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
    return loadTypeDao.findById(loadTypeId).orElseThrow(() -> new RuntimeException()); // FIXME
  }

  private List<MuscleGroup> getMuscleGroups(final Set<UUID> muscleGroupsIds) {
    final List<MuscleGroup> muscleGroups =
        muscleGroupsIds.isEmpty()
            ? Collections.emptyList()
            : muscleGroupDao.findAllById(muscleGroupsIds);

    if (muscleGroups.size() != muscleGroupsIds.size()) {
      final List<UUID> foundedIds = muscleGroups.stream().map(BaseEntity::getId).toList();
      throw new RuntimeException(); // FIXME
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
      throw new RuntimeException(); // FIXME
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
      final CreateExerciseRequestApplication request)
      throws EntityNotFoundException {
    final List<MuscleGroupExercise> muscleGroupExercises =
        new ArrayList<>(request.getMuscleGroups().size());

    for (MuscleGroupExercises mge : request.getMuscleGroups()) {
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
}*/
