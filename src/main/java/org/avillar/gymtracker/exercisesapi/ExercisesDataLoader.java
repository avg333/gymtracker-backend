package org.avillar.gymtracker.exercisesapi;

import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.exercisesapi.exercise.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.loadtype.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.loadtype.domain.LoadTypeDao;
import org.avillar.gymtracker.exercisesapi.musclegroup.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.musclegroup.domain.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.musclegroup.domain.MuscleGroupExercise;
import org.avillar.gymtracker.exercisesapi.musclegroup.domain.MuscleGroupExerciseDao;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.musclesubgroup.domain.MuscleSubGroupDao;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.musclesupgroup.domain.MuscleSupGroupDao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExercisesDataLoader implements ApplicationRunner {

  private final LoadTypeDao loadTypeDao;
  private final MuscleSupGroupDao muscleSupGroupDao;
  private final MuscleGroupDao muscleGroupDao;
  private final MuscleSubGroupDao muscleSubGroupDao;
  private final ExerciseDao exerciseDao;
  private final MuscleGroupExerciseDao muscleGroupExerciseDao;

  public void run(ApplicationArguments args) {
    final long start = System.currentTimeMillis();
    if (!exerciseDao.findAll().isEmpty()) {
      log.info("Micro exercises is already populated");
      return;
    }
    log.info("Populating exercises micro...");

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------LOAD TYPES-------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var BAR = new LoadType("BAR", null, new HashSet<>());
    var DUMBBELL = new LoadType("DUMBBELL", null, new HashSet<>());
    var CABLE = new LoadType("CABLE", null, new HashSet<>());
    var BODYWEIGHT = new LoadType("BODYWEIGHT", null, new HashSet<>());
    var MACHINE = new LoadType("MACHINE", null, new HashSet<>());
    var MULTIPOWER = new LoadType("MULTIPOWER", null, new HashSet<>());
    var loadTypes = List.of(BAR, DUMBBELL, CABLE, BODYWEIGHT, MACHINE, MULTIPOWER);
    log.info("\tInserting " + loadTypes.size() + " load types...");
    loadTypeDao.saveAll(loadTypes);
    log.info("\tInserting " + loadTypes.size() + " load types");

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------MUSCLE SUP GROUPS------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var CHEST_MSUPG = new MuscleSupGroup("CHEST", null, new HashSet<>());
    var BACK = new MuscleSupGroup("BACK", null, new HashSet<>());
    var SHOULDERS = new MuscleSupGroup("SHOULDERS", null, new HashSet<>());
    var ARMS = new MuscleSupGroup("ARMS", null, new HashSet<>());
    var CORE = new MuscleSupGroup("CORE", null, new HashSet<>());
    var LEGS = new MuscleSupGroup("LEGS", null, new HashSet<>());
    var muscleSupGroups = List.of(CHEST_MSUPG, BACK, SHOULDERS, ARMS, CORE, LEGS);
    log.info("\tInserting " + muscleSupGroups.size() + " muscleSupGroups...");
    muscleSupGroupDao.saveAll(muscleSupGroups);
    log.info("\tInserting " + muscleSupGroups.size() + " muscleSupGroups");

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------MUSCLE GROUPS----------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var CHEST =
        new MuscleGroup(
            "CHEST", null, new HashSet<>(List.of(CHEST_MSUPG)), new HashSet<>(), new HashSet<>());
    var SHOULDER_ANTERIOR =
        new MuscleGroup(
            "SHOULDER ANTERIOR",
            null,
            new HashSet<>(List.of(SHOULDERS, CHEST_MSUPG)),
            new HashSet<>(),
            new HashSet<>());
    var SHOULDER_LATERAL =
        new MuscleGroup(
            "SHOULDER LATERAL",
            null,
            new HashSet<>(List.of(SHOULDERS)),
            new HashSet<>(),
            new HashSet<>());
    var SHOULDER_POSTERIOR =
        new MuscleGroup(
            "SHOULDER POSTERIOR",
            null,
            new HashSet<>(List.of(SHOULDERS, BACK)),
            new HashSet<>(),
            new HashSet<>());
    var LATS =
        new MuscleGroup(
            "LATS", null, new HashSet<>(List.of(BACK)), new HashSet<>(), new HashSet<>());
    var TRAPS =
        new MuscleGroup(
            "TRAPS", null, new HashSet<>(List.of(BACK)), new HashSet<>(), new HashSet<>());
    var LOWER_BACK =
        new MuscleGroup(
            "LOWER BACK",
            null,
            new HashSet<>(List.of(BACK, CORE)),
            new HashSet<>(),
            new HashSet<>());
    var BICEPS =
        new MuscleGroup(
            "BICEPS", null, new HashSet<>(List.of(ARMS)), new HashSet<>(), new HashSet<>());
    var TRICEPS =
        new MuscleGroup(
            "TRICEPS", null, new HashSet<>(List.of(ARMS)), new HashSet<>(), new HashSet<>());
    var FOREARMS =
        new MuscleGroup(
            "FOREARMS", null, new HashSet<>(List.of(ARMS)), new HashSet<>(), new HashSet<>());
    var ABS =
        new MuscleGroup(
            "ABS", null, new HashSet<>(List.of(CORE)), new HashSet<>(), new HashSet<>());
    var QUADS =
        new MuscleGroup(
            "QUADS", null, new HashSet<>(List.of(LEGS)), new HashSet<>(), new HashSet<>());
    var HAMSTRINGS =
        new MuscleGroup(
            "HAMSTRINGS", null, new HashSet<>(List.of(LEGS)), new HashSet<>(), new HashSet<>());
    var GLUTES =
        new MuscleGroup(
            "GLUTES", null, new HashSet<>(List.of(LEGS)), new HashSet<>(), new HashSet<>());
    var CALVES =
        new MuscleGroup(
            "CALVES", null, new HashSet<>(List.of(LEGS)), new HashSet<>(), new HashSet<>());
    var TIBIALES_ANTERIOR =
        new MuscleGroup(
            "TIBIALES ANTERIOR",
            null,
            new HashSet<>(List.of(LEGS)),
            new HashSet<>(),
            new HashSet<>());
    var muscleGroups =
        List.of(
            CHEST,
            SHOULDER_ANTERIOR,
            SHOULDER_LATERAL,
            SHOULDER_POSTERIOR,
            LATS,
            TRAPS,
            LOWER_BACK,
            BICEPS,
            TRICEPS,
            FOREARMS,
            ABS,
            QUADS,
            HAMSTRINGS,
            GLUTES,
            CALVES,
            TIBIALES_ANTERIOR);
    log.info("\tInserting " + muscleGroups.size() + " muscleGroups...");
    muscleGroupDao.saveAll(muscleGroups);
    log.info("\tInserting " + muscleGroups.size() + " muscleGroups");

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------MUSCLE SUB GROUPS------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var CHEST_UPPER = new MuscleSubGroup("CHEST UPPER", null, CHEST, new HashSet<>());
    var CHEST_LOWER = new MuscleSubGroup("CHEST LOWER", null, CHEST, new HashSet<>());
    var CHEST_MIDDLE = new MuscleSubGroup("CHEST MIDDLE", null, CHEST, new HashSet<>());
    var LATS_UPPER = new MuscleSubGroup("LATS UPPER", null, LATS, new HashSet<>());
    var LATS_LOWER = new MuscleSubGroup("LATS LOWER", null, LATS, new HashSet<>());
    var TRICEPS_LONG = new MuscleSubGroup("TRICEPS LONG", null, TRICEPS, new HashSet<>());
    var TRICEPS_SHORT = new MuscleSubGroup("TRICEPS SHORT", null, TRICEPS, new HashSet<>());
    var TRICEPS_MIDDLE = new MuscleSubGroup("TRICEPS MIDDLE", null, TRICEPS, new HashSet<>());
    var FOREARMS_FLEXORS = new MuscleSubGroup("FOREARMS FLEXORS", null, FOREARMS, new HashSet<>());
    var FOREARMS_EXTENSORS =
        new MuscleSubGroup("FOREARMS EXTENSORS", null, FOREARMS, new HashSet<>());
    var FOREARMS_BRACHIORADIALIS =
        new MuscleSubGroup("FOREARMS BRACHIORADIALIS", null, FOREARMS, new HashSet<>());
    var muscleSubGroups =
        List.of(
            CHEST_UPPER,
            CHEST_LOWER,
            CHEST_MIDDLE,
            LATS_UPPER,
            LATS_LOWER,
            TRICEPS_LONG,
            TRICEPS_SHORT,
            TRICEPS_MIDDLE,
            FOREARMS_FLEXORS,
            FOREARMS_EXTENSORS,
            FOREARMS_BRACHIORADIALIS);
    log.info("\tInserting " + muscleSubGroups.size() + " muscleSupGroups...");
    muscleSubGroupDao.saveAll(muscleSubGroups);
    log.info("\tInserting " + muscleSubGroups.size() + " muscleSupGroups");

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------EXERCISES--------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var PRESS_BANCA =
        new Exercise("PRESS BANCA", null, false, BAR, new HashSet<>(), new HashSet<>());
    var PRESS_BANCA_INCLINADO =
        new Exercise(
            "PRESS BANCA INCLINADO",
            null,
            false,
            BAR,
            new HashSet<>(List.of(CHEST_UPPER)),
            new HashSet<>());
    var exercises = List.of(PRESS_BANCA, PRESS_BANCA_INCLINADO);
    log.info("\tInserting " + exercises.size() + " exercises...");
    exerciseDao.saveAll(exercises);
    log.info("\tInserting " + exercises.size() + " exercises");

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------EXERCISES_MUSCLE_GROUPS------------------------------------
    // ---------------------------------------------------------------------------------------------
    var PRESS_BANCA_CHEST = new MuscleGroupExercise(1.0, CHEST, PRESS_BANCA);
    var PRESS_BANCA_INCL_CHEST = new MuscleGroupExercise(1.0, CHEST, PRESS_BANCA_INCLINADO);
    var exercisesMuscleGroups = List.of(PRESS_BANCA_CHEST, PRESS_BANCA_INCL_CHEST);
    log.info("\tInserting " + exercisesMuscleGroups.size() + " exercises-muscleGroups...");
    muscleGroupExerciseDao.saveAll(exercisesMuscleGroups);
    log.info("\tInserting " + exercisesMuscleGroups.size() + " exercises-muscleGroups");

    int totalInserts =
        loadTypes.size()
            + muscleSupGroups.size()
            + muscleGroups.size()
            + muscleSubGroups.size()
            + exercises.size()
            + exercisesMuscleGroups.size();
    long finish = System.currentTimeMillis();
    log.info(
        "Populated exercise micro with "
            + totalInserts
            + " enitities in "
            + (finish - start)
            + "ms");
  }
}
