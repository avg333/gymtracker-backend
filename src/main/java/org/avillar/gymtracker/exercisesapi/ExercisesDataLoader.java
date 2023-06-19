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
    var bar = new LoadType("BAR", null, new HashSet<>());
    var dumbbell = new LoadType("DUMBBELL", null, new HashSet<>());
    var cable = new LoadType("CABLE", null, new HashSet<>());
    var bodyweight = new LoadType("BODYWEIGHT", null, new HashSet<>());
    var machine = new LoadType("MACHINE", null, new HashSet<>());
    var multipower = new LoadType("MULTIPOWER", null, new HashSet<>());
    var loadTypes = List.of(bar, dumbbell, cable, bodyweight, machine, multipower);
    log.info("\tInserting " + loadTypes.size() + " load types...");
    loadTypeDao.saveAll(loadTypes);
    log.info("\tInserting " + loadTypes.size() + " load types");

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------MUSCLE SUP GROUPS------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var chestMsupg = new MuscleSupGroup("CHEST", null, new HashSet<>());
    var back = new MuscleSupGroup("BACK", null, new HashSet<>());
    var shoulders = new MuscleSupGroup("SHOULDERS", null, new HashSet<>());
    var arms = new MuscleSupGroup("ARMS", null, new HashSet<>());
    var core = new MuscleSupGroup("CORE", null, new HashSet<>());
    var legs = new MuscleSupGroup("LEGS", null, new HashSet<>());
    var muscleSupGroups = List.of(chestMsupg, back, shoulders, arms, core, legs);
    log.info("\tInserting " + muscleSupGroups.size() + " muscleSupGroups...");
    muscleSupGroupDao.saveAll(muscleSupGroups);
    log.info("\tInserting " + muscleSupGroups.size() + " muscleSupGroups");

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------MUSCLE GROUPS----------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var chest =
        new MuscleGroup(
            "CHEST", null, new HashSet<>(List.of(chestMsupg)), new HashSet<>(), new HashSet<>());
    var shoulderAnterior =
        new MuscleGroup(
            "SHOULDER ANTERIOR",
            null,
            new HashSet<>(List.of(shoulders, chestMsupg)),
            new HashSet<>(),
            new HashSet<>());
    var shoulderLateral =
        new MuscleGroup(
            "SHOULDER LATERAL",
            null,
            new HashSet<>(List.of(shoulders)),
            new HashSet<>(),
            new HashSet<>());
    var shoulderPosterior =
        new MuscleGroup(
            "SHOULDER POSTERIOR",
            null,
            new HashSet<>(List.of(shoulders, back)),
            new HashSet<>(),
            new HashSet<>());
    var lats =
        new MuscleGroup(
            "LATS", null, new HashSet<>(List.of(back)), new HashSet<>(), new HashSet<>());
    var traps =
        new MuscleGroup(
            "TRAPS", null, new HashSet<>(List.of(back)), new HashSet<>(), new HashSet<>());
    var lowerBack =
        new MuscleGroup(
            "LOWER BACK",
            null,
            new HashSet<>(List.of(back, core)),
            new HashSet<>(),
            new HashSet<>());
    var biceps =
        new MuscleGroup(
            "BICEPS", null, new HashSet<>(List.of(arms)), new HashSet<>(), new HashSet<>());
    var triceps =
        new MuscleGroup(
            "TRICEPS", null, new HashSet<>(List.of(arms)), new HashSet<>(), new HashSet<>());
    var forearms =
        new MuscleGroup(
            "FOREARMS", null, new HashSet<>(List.of(arms)), new HashSet<>(), new HashSet<>());
    var abs =
        new MuscleGroup(
            "ABS", null, new HashSet<>(List.of(core)), new HashSet<>(), new HashSet<>());
    var quads =
        new MuscleGroup(
            "QUADS", null, new HashSet<>(List.of(legs)), new HashSet<>(), new HashSet<>());
    var hamstrings =
        new MuscleGroup(
            "HAMSTRINGS", null, new HashSet<>(List.of(legs)), new HashSet<>(), new HashSet<>());
    var glutes =
        new MuscleGroup(
            "GLUTES", null, new HashSet<>(List.of(legs)), new HashSet<>(), new HashSet<>());
    var calves =
        new MuscleGroup(
            "CALVES", null, new HashSet<>(List.of(legs)), new HashSet<>(), new HashSet<>());
    var tibialesAnterior =
        new MuscleGroup(
            "TIBIALES ANTERIOR",
            null,
            new HashSet<>(List.of(legs)),
            new HashSet<>(),
            new HashSet<>());
    var muscleGroups =
        List.of(
            chest,
            shoulderAnterior,
            shoulderLateral,
            shoulderPosterior,
            lats,
            traps,
            lowerBack,
            biceps,
            triceps,
            forearms,
            abs,
            quads,
            hamstrings,
            glutes,
            calves,
            tibialesAnterior);
    log.info("\tInserting " + muscleGroups.size() + " muscleGroups...");
    muscleGroupDao.saveAll(muscleGroups);
    log.info("\tInserting " + muscleGroups.size() + " muscleGroups");

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------MUSCLE SUB GROUPS------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var chestUpper = new MuscleSubGroup("CHEST UPPER", null, chest, new HashSet<>());
    var chestLower = new MuscleSubGroup("CHEST LOWER", null, chest, new HashSet<>());
    var chestMiddle = new MuscleSubGroup("CHEST MIDDLE", null, chest, new HashSet<>());
    var latsUpper = new MuscleSubGroup("LATS UPPER", null, lats, new HashSet<>());
    var latsLower = new MuscleSubGroup("LATS LOWER", null, lats, new HashSet<>());
    var tricepsLong = new MuscleSubGroup("TRICEPS LONG", null, triceps, new HashSet<>());
    var tricepsShort = new MuscleSubGroup("TRICEPS SHORT", null, triceps, new HashSet<>());
    var tricepsMiddle = new MuscleSubGroup("TRICEPS MIDDLE", null, triceps, new HashSet<>());
    var forearmsFlexors = new MuscleSubGroup("FOREARMS FLEXORS", null, forearms, new HashSet<>());
    var forearmsExtensors =
        new MuscleSubGroup("FOREARMS EXTENSORS", null, forearms, new HashSet<>());
    var forearmsBrachioradialis =
        new MuscleSubGroup("FOREARMS BRACHIORADIALIS", null, forearms, new HashSet<>());
    var muscleSubGroups =
        List.of(
            chestUpper,
            chestLower,
            chestMiddle,
            latsUpper,
            latsLower,
            tricepsLong,
            tricepsShort,
            tricepsMiddle,
            forearmsFlexors,
            forearmsExtensors,
            forearmsBrachioradialis);
    log.info("\tInserting " + muscleSubGroups.size() + " muscleSupGroups...");
    muscleSubGroupDao.saveAll(muscleSubGroups);
    log.info("\tInserting " + muscleSubGroups.size() + " muscleSupGroups");

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------EXERCISES--------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var pressBanca =
        new Exercise("PRESS BANCA", null, false, bar, new HashSet<>(), new HashSet<>());
    var pressBancaInclinado =
        new Exercise(
            "PRESS BANCA INCLINADO",
            null,
            false,
            bar,
            new HashSet<>(List.of(chestUpper)),
            new HashSet<>());
    var pressBancaDeclinado =
        new Exercise(
            "PRESS BANCA DECLINADO",
            null,
            false,
            bar,
            new HashSet<>(List.of(chestLower)),
            new HashSet<>());
    var pressMancuernas =
        new Exercise("PRESS CON MANCUERNAS", null, false, bar, new HashSet<>(), new HashSet<>());
    var pressMancuernasInclinado =
        new Exercise(
            "PRESS CON MANCUERNAS INCLINADO",
            null,
            false,
            bar,
            new HashSet<>(List.of(chestUpper)),
            new HashSet<>());
    var pressMancuernasDeclinado =
        new Exercise(
            "PRESS CON MANCUERNAS DECLINADO",
            null,
            false,
            bar,
            new HashSet<>(List.of(chestLower)),
            new HashSet<>());
    var pressMultipower =
        new Exercise("PRESS EN MULTIPOWER", null, false, bar, new HashSet<>(), new HashSet<>());
    var pressMultipowerInclinado =
        new Exercise(
            "PRESS EN MULTIPOWER INCLINADO",
            null,
            false,
            bar,
            new HashSet<>(List.of(chestUpper)),
            new HashSet<>());
    var pressMultipowerDeclinado =
        new Exercise(
            "PRESS EN MULTIPOWER DECLINADO",
            null,
            false,
            bar,
            new HashSet<>(List.of(chestLower)),
            new HashSet<>());
    var aperturasMancuernas =
        new Exercise(
            "PRESS CON MANCUERNAS",
            null,
            false,
            bar,
            new HashSet<>(List.of(chestMiddle)),
            new HashSet<>());
    var aperturasMancuernasInclinado =
        new Exercise(
            "PRESS CON MANCUERNAS INCLINADO",
            null,
            false,
            bar,
            new HashSet<>(List.of(chestUpper, chestMiddle)),
            new HashSet<>());
    var aperturasMancuernasDeclinado =
        new Exercise(
            "PRESS CON MANCUERNAS DECLINADO",
            null,
            false,
            bar,
            new HashSet<>(List.of(chestLower, chestMiddle)),
            new HashSet<>());
    var exercises =
        List.of(
            pressBanca,
            pressBancaInclinado,
            pressBancaDeclinado,
            pressMancuernas,
            pressMancuernasInclinado,
            pressMancuernasDeclinado,
            pressMultipower,
            pressMultipowerInclinado,
            pressMultipowerDeclinado,
            aperturasMancuernas,
            aperturasMancuernasInclinado,
            aperturasMancuernasDeclinado);
    log.info("\tInserting " + exercises.size() + " exercises...");
    exerciseDao.saveAll(exercises);
    log.info("\tInserting " + exercises.size() + " exercises");

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------EXERCISES_MUSCLE_GROUPS------------------------------------
    // ---------------------------------------------------------------------------------------------
    var pressBancaChest = new MuscleGroupExercise(1.0, chest, pressBanca);
    var pressBancaInclChest = new MuscleGroupExercise(1.0, chest, pressBancaInclinado);
    var press1 = new MuscleGroupExercise(1.0, chest, pressBancaDeclinado);
    var press2 = new MuscleGroupExercise(1.0, chest, pressMancuernas);
    var press3 = new MuscleGroupExercise(1.0, chest, pressMancuernasInclinado);
    var press4 = new MuscleGroupExercise(1.0, chest, pressMancuernasDeclinado);
    var press5 = new MuscleGroupExercise(1.0, chest, pressMultipower);
    var press6 = new MuscleGroupExercise(1.0, chest, pressMultipowerInclinado);
    var press7 = new MuscleGroupExercise(1.0, chest, pressMultipowerDeclinado);
    var press8 = new MuscleGroupExercise(1.0, chest, aperturasMancuernas);
    var press9 = new MuscleGroupExercise(1.0, chest, aperturasMancuernasInclinado);
    var press10 = new MuscleGroupExercise(1.0, chest, aperturasMancuernasDeclinado);

    var exercisesMuscleGroups =
        List.of(
            pressBancaChest,
            pressBancaInclChest,
            press1,
            press2,
            press3,
            press4,
            press5,
            press6,
            press7,
            press8,
            press9,
            press10);
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
