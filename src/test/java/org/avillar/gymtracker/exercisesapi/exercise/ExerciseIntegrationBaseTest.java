package org.avillar.gymtracker.exercisesapi.exercise;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.IntegrationBaseTest;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
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
import org.avillar.gymtracker.exercisesapi.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSupGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

public abstract class ExerciseIntegrationBaseTest extends IntegrationBaseTest {

  private final Random rand = new Random();

  private static final int TOTAL_LOAD_TYPES = 5;
  private static final int TOTAL_M_SUP_G = 3;
  private static final int TOTAL_M_G = 4;
  private static final int TOTAL_M_SUB_G = 5;
  private static final int TOTAL_PUBLIC_EXERCISES = 10;
  private static final int TOTAL_PRIVATE_EXERCISES_PER_USER = 3;

  @Autowired protected LoadTypeDao loadTypeDao;
  @Autowired protected MuscleSupGroupDao muscleSupGroupDao;
  @Autowired protected MuscleGroupDao muscleGroupDao;
  @Autowired protected MuscleSubGroupDao muscleSubGroupDao;
  @Autowired protected ExerciseDao exerciseDao;
  @Autowired protected MuscleGroupExerciseDao muscleGroupExerciseDao;

  private void createLoadTypes() {
    loadTypeDao.deleteAll();
    final List<LoadType> loadTypes = easyRandom.objects(LoadType.class, TOTAL_LOAD_TYPES).toList();
    loadTypes.forEach(
        loadType -> {
          loadType.setId(null);
          loadType.setExercises(new HashSet<>());
        });
    loadTypeDao.saveAll(loadTypes);
  }

  private void createMuscleGroups() {
    muscleSubGroupDao.deleteAll();
    muscleGroupDao.deleteAll();
    muscleSupGroupDao.deleteAll();

    final List<MuscleSupGroup> muscleSupGroups =
        easyRandom.objects(MuscleSupGroup.class, TOTAL_M_SUP_G).toList();
    final List<MuscleGroup> muscleGroups = new ArrayList<>();
    final List<MuscleSubGroup> muscleSubGroups = new ArrayList<>();
    muscleSupGroups.forEach(
        mSupG -> {
          mSupG.setId(null);
          mSupG.setMuscleGroups(new HashSet<>());
          final List<MuscleGroup> muscleGroupsAux =
              easyRandom.objects(MuscleGroup.class, TOTAL_M_G).toList();
          muscleGroupsAux.forEach(
              mg -> {
                mg.setId(null);
                mg.setMuscleSubGroups(new HashSet<>());
                mg.setMuscleSupGroups(Set.of(mSupG));
                final List<MuscleSubGroup> muscleSubGroupsAux =
                    easyRandom.objects(MuscleSubGroup.class, TOTAL_M_SUB_G).toList();
                muscleSubGroupsAux.forEach(
                    mSubG -> {
                      mSubG.setId(null);
                      mSubG.setMuscleGroup(mg);
                    });
                muscleSubGroups.addAll(muscleSubGroupsAux);
              });
          muscleGroups.addAll(muscleGroupsAux);
        });
    muscleSupGroupDao.saveAll(muscleSupGroups);
    muscleGroupDao.saveAll(muscleGroups);
    muscleSubGroupDao.saveAll(muscleSubGroups);
  }

  private void createExercises() {
    muscleGroupExerciseDao.deleteAll();
    exerciseDao.deleteAll();

    final List<LoadType> loadTypes = loadTypeDao.findAll();
    final List<MuscleGroup> muscleGroups = muscleGroupDao.findAll();

    final List<Exercise> exercises = new ArrayList<>();
    for (int i = 0; i < TOTAL_PUBLIC_EXERCISES; i++) {
      exercises.add(generateRandomExercise(loadTypes, null));
    }
    for (final UserApp userApp : userDao.findAll()) {
      for (int i = 0; i < TOTAL_PUBLIC_EXERCISES; i++) {
        exercises.add(generateRandomExercise(loadTypes, userApp));
      }
    }

    exerciseDao.saveAll(exercises);
    muscleGroupExerciseDao.saveAll(
        exercises.stream()
            .map(exercise -> generateMuscleGroupExercise(exercise, muscleGroups))
            .toList());
  }

  private Exercise generateRandomExercise(final List<LoadType> loadTypes, final UserApp userApp) {
    final Exercise exercise = new Exercise();
    exercise.setName(easyRandom.nextObject(String.class));
    exercise.setDescription(easyRandom.nextObject(String.class));
    exercise.setAccessType(userApp == null ? AccessTypeEnum.PUBLIC : AccessTypeEnum.PRIVATE);
    exercise.setOwner(userApp == null ? UUID.randomUUID() : userApp.getId());
    exercise.setUnilateral(easyRandom.nextBoolean());
    exercise.setLoadType(loadTypes.get(getRandomIndexOfList(loadTypes)));

    return exercise;
  }

  private MuscleGroupExercise generateMuscleGroupExercise(
      final Exercise exercise, final List<MuscleGroup> muscleGroups) {
    final MuscleGroupExercise muscleGroupExercise = new MuscleGroupExercise();
    muscleGroupExercise.setExercise(exercise);
    muscleGroupExercise.setMuscleGroup(muscleGroups.get(getRandomIndexOfList(muscleGroups)));
    muscleGroupExercise.setWeight(1d);

    return muscleGroupExercise;
  }

  private int getRandomIndexOfList(List<?> list) {
    return rand.nextInt(list.size());
  }

  protected Exercise getPublicExercise() {
    return exerciseDao.findAll().stream()
        .filter(ex -> ex.getAccessType() == AccessTypeEnum.PUBLIC)
        .findAny()
        .orElseThrow(() -> new RuntimeException("No public exercises found"));
  }

  protected Exercise getPrivateExerciseOfUser() {
    final UserApp userApp =
        userDao.findAll().stream()
            .filter(user -> user.getUsername().equals(IntegrationBaseTest.USER_NAME_OK))
            .findAny()
            .orElseThrow(() -> new RuntimeException("User not found"));

    return exerciseDao.findAll().stream()
        .filter(ex -> ex.getAccessType() == AccessTypeEnum.PRIVATE)
        .filter(ex -> ex.getOwner().equals(userApp.getId()))
        .findAny()
        .orElseThrow(() -> new RuntimeException("Exercise private not found"));
  }

  protected Exercise getPrivateExerciseOfOtherUser() {
    final UserApp userApp =
        userDao.findAll().stream()
            .filter(user -> user.getUsername().equals(IntegrationBaseTest.USER_NAME_OK))
            .findAny()
            .orElseThrow(() -> new RuntimeException("User not found"));

    return exerciseDao.findAll().stream()
        .filter(ex -> ex.getAccessType() == AccessTypeEnum.PRIVATE)
        .filter(ex -> !ex.getOwner().equals(userApp.getId()))
        .findAny()
        .orElseThrow(() -> new RuntimeException("Exercise private not found"));
  }

  protected void createAll() {
    createUsers();
    createLoadTypes();
    createMuscleGroups();
    createExercises();
  }

  protected void deleteAll() {
    muscleGroupExerciseDao.deleteAll();
    exerciseDao.deleteAll();
    muscleSubGroupDao.deleteAll();
    muscleGroupDao.deleteAll();
    muscleSupGroupDao.deleteAll();
    loadTypeDao.deleteAll();
    deleteUsers();
  }

  protected UUID getUserId() {
    return userDao.findAll().stream()
        .filter(user -> user.getUsername().equals(IntegrationBaseTest.USER_NAME_OK))
        .findAny()
        .orElseThrow(() -> new RuntimeException("User not found"))
        .getId();
  }

  protected void assertResultActions(final ResultActions resultActions, final Exercise exercise)
      throws Exception {
    resultActions
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(exercise.getId().toString()))
        .andExpect(jsonPath("$.name").value(exercise.getName()))
        .andExpect(jsonPath("$.description").value(exercise.getDescription()))
        .andExpect(jsonPath("$.loadType.id").value(exercise.getLoadType().getId().toString()));
  }
}
