package org.avillar.gymtracker.exercisesapi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.common.adapter.repository.UserDao;
import org.avillar.gymtracker.authapi.common.adapter.repository.model.UserEntity;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.ExerciseUsesDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.LoadTypeDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleGroupExerciseDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleSubGroupDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleSupGroupDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseUsesEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.LoadTypeEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupExerciseEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSubGroupEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSupGroupEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExercisesDataLoader implements ApplicationRunner {

  private final UserDao userDao;
  private final ExerciseUsesDao exerciseUsesDao;
  private final LoadTypeDao loadTypeDao;
  private final MuscleSupGroupDao muscleSupGroupDao;
  private final MuscleGroupDao muscleGroupDao;
  private final MuscleSubGroupDao muscleSubGroupDao;
  private final ExerciseDao exerciseDao;
  private final MuscleGroupExerciseDao muscleGroupExerciseDao;

  @Value("${spring.profiles.active}")
  private String activeProfile;

  public void run(ApplicationArguments args) {
    final long start = System.currentTimeMillis();
    if (activeProfile.equals("test")) {
      return;
    } else if (!exerciseDao.findAll().isEmpty()) {
      log.info("Micro exercises is already populated");
      return;
    }

    log.info("Populating exercises micro...");
    int totalInserts = createExercises();

    long finish = System.currentTimeMillis();
    log.info("Populated exercise micro with {} entities in {} ms", totalInserts, finish - start);
  }

  private int createExercises() {
    // ---------------------------------------------------------------------------------------------
    // ----------------------------------LOAD TYPES-------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var bar = new LoadTypeEntity(null, "BAR", null, new HashSet<>());
    var dumbbell = new LoadTypeEntity(null, "DUMBBELL", null, new HashSet<>());
    var cable = new LoadTypeEntity(null, "CABLE", null, new HashSet<>());
    var bodyweight = new LoadTypeEntity(null, "BODYWEIGHT", null, new HashSet<>());
    var machine = new LoadTypeEntity(null, "MACHINE", null, new HashSet<>());
    var multipower = new LoadTypeEntity(null, "MULTIPOWER", null, new HashSet<>());
    var loadTypes = List.of(bar, dumbbell, cable, bodyweight, machine, multipower);
    log.info("\tInserting {} load types...", loadTypes.size());
    loadTypeDao.saveAll(loadTypes);
    log.info("\tInserting {} load types", loadTypes.size());

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------MUSCLE SUP GROUPS------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var chestMsupg = new MuscleSupGroupEntity(null, "CHEST", null, new HashSet<>());
    var back = new MuscleSupGroupEntity(null, "BACK", null, new HashSet<>());
    var shoulders = new MuscleSupGroupEntity(null, "SHOULDERS", null, new HashSet<>());
    var arms = new MuscleSupGroupEntity(null, "ARMS", null, new HashSet<>());
    var core = new MuscleSupGroupEntity(null, "CORE", null, new HashSet<>());
    var legs = new MuscleSupGroupEntity(null, "LEGS", null, new HashSet<>());
    var muscleSupGroups = List.of(chestMsupg, back, shoulders, arms, core, legs);
    log.info("\tInserting {} muscleSupGroups...", muscleSupGroups.size());
    muscleSupGroupDao.saveAll(muscleSupGroups);
    log.info("\tInserting {} muscleSupGroups", muscleSupGroups.size());

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------MUSCLE GROUPS----------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var chest =
        new MuscleGroupEntity(
            null,
            "CHEST",
            null,
            new HashSet<>(List.of(chestMsupg)),
            new HashSet<>(),
            new HashSet<>());
    var shoulderAnterior =
        new MuscleGroupEntity(
            null,
            "SHOULDER ANTERIOR",
            null,
            new HashSet<>(List.of(shoulders, chestMsupg)),
            new HashSet<>(),
            new HashSet<>());
    var shoulderLateral =
        new MuscleGroupEntity(
            null,
            "SHOULDER LATERAL",
            null,
            new HashSet<>(List.of(shoulders)),
            new HashSet<>(),
            new HashSet<>());
    var shoulderPosterior =
        new MuscleGroupEntity(
            null,
            "SHOULDER POSTERIOR",
            null,
            new HashSet<>(List.of(shoulders, back)),
            new HashSet<>(),
            new HashSet<>());
    var lats =
        new MuscleGroupEntity(
            null, "LATS", null, new HashSet<>(List.of(back)), new HashSet<>(), new HashSet<>());
    var traps =
        new MuscleGroupEntity(
            null, "TRAPS", null, new HashSet<>(List.of(back)), new HashSet<>(), new HashSet<>());
    var lowerBack =
        new MuscleGroupEntity(
            null,
            "LOWER BACK",
            null,
            new HashSet<>(List.of(back, core)),
            new HashSet<>(),
            new HashSet<>());
    var biceps =
        new MuscleGroupEntity(
            null, "BICEPS", null, new HashSet<>(List.of(arms)), new HashSet<>(), new HashSet<>());
    var triceps =
        new MuscleGroupEntity(
            null, "TRICEPS", null, new HashSet<>(List.of(arms)), new HashSet<>(), new HashSet<>());
    var forearms =
        new MuscleGroupEntity(
            null, "FOREARMS", null, new HashSet<>(List.of(arms)), new HashSet<>(), new HashSet<>());
    var abs =
        new MuscleGroupEntity(
            null, "ABS", null, new HashSet<>(List.of(core)), new HashSet<>(), new HashSet<>());
    var quads =
        new MuscleGroupEntity(
            null, "QUADS", null, new HashSet<>(List.of(legs)), new HashSet<>(), new HashSet<>());
    var hamstrings =
        new MuscleGroupEntity(
            null,
            "HAMSTRINGS",
            null,
            new HashSet<>(List.of(legs)),
            new HashSet<>(),
            new HashSet<>());
    var glutes =
        new MuscleGroupEntity(
            null, "GLUTES", null, new HashSet<>(List.of(legs)), new HashSet<>(), new HashSet<>());
    var calves =
        new MuscleGroupEntity(
            null, "CALVES", null, new HashSet<>(List.of(legs)), new HashSet<>(), new HashSet<>());
    var tibialesAnterior =
        new MuscleGroupEntity(
            null,
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
    log.info("\tInserting {} muscleGroups...", muscleGroups.size());
    muscleGroupDao.saveAll(muscleGroups);
    log.info("\tInserting {} muscleGroups", muscleGroups.size());

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------MUSCLE SUB GROUPS------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var chestUpper = new MuscleSubGroupEntity(null, "CHEST UPPER", null, chest, new HashSet<>());
    var chestLower = new MuscleSubGroupEntity(null, "CHEST LOWER", null, chest, new HashSet<>());
    var chestMiddle = new MuscleSubGroupEntity(null, "CHEST MIDDLE", null, chest, new HashSet<>());
    var latsUpper = new MuscleSubGroupEntity(null, "LATS UPPER", null, lats, new HashSet<>());
    var latsLower = new MuscleSubGroupEntity(null, "LATS LOWER", null, lats, new HashSet<>());
    var tricepsLong =
        new MuscleSubGroupEntity(null, "TRICEPS LONG", null, triceps, new HashSet<>());
    var tricepsShort =
        new MuscleSubGroupEntity(null, "TRICEPS SHORT", null, triceps, new HashSet<>());
    var tricepsMiddle =
        new MuscleSubGroupEntity(null, "TRICEPS MIDDLE", null, triceps, new HashSet<>());
    var forearmsFlexors =
        new MuscleSubGroupEntity(null, "FOREARMS FLEXORS", null, forearms, new HashSet<>());
    var forearmsExtensors =
        new MuscleSubGroupEntity(null, "FOREARMS EXTENSORS", null, forearms, new HashSet<>());
    var forearmsBrachioradialis =
        new MuscleSubGroupEntity(null, "FOREARMS BRACHIORADIALIS", null, forearms, new HashSet<>());
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
    log.info("\tInserting {} muscleSupGroups...", muscleSubGroups.size());
    muscleSubGroupDao.saveAll(muscleSubGroups);
    log.info("\tInserting {} muscleSupGroups", muscleSubGroups.size());

    final UUID adminId = UUID.randomUUID();
    // ---------------------------------------------------------------------------------------------
    // ----------------------------------EXERCISES--------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    var pressBanca =
        new ExerciseEntity(
            null,
            "PRESS BANCA",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            bar,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>());
    var pressBancaInclinado =
        new ExerciseEntity(
            null,
            "PRESS BANCA INCLINADO",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            bar,
            new HashSet<>(List.of(chestUpper)),
            new HashSet<>(),
            new HashSet<>());
    var pressBancaDeclinado =
        new ExerciseEntity(
            null,
            "PRESS BANCA DECLINADO",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            bar,
            new HashSet<>(List.of(chestLower)),
            new HashSet<>(),
            new HashSet<>());
    var pressMancuernas =
        new ExerciseEntity(
            null,
            "PRESS CON MANCUERNAS",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            dumbbell,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>());
    var pressMancuernasInclinado =
        new ExerciseEntity(
            null,
            "PRESS CON MANCUERNAS INCLINADO",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            dumbbell,
            new HashSet<>(List.of(chestUpper)),
            new HashSet<>(),
            new HashSet<>());
    var pressMancuernasDeclinado =
        new ExerciseEntity(
            null,
            "PRESS CON MANCUERNAS DECLINADO",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            dumbbell,
            new HashSet<>(List.of(chestLower)),
            new HashSet<>(),
            new HashSet<>());
    var pressMultipower =
        new ExerciseEntity(
            null,
            "PRESS EN MULTIPOWER",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            multipower,
            new HashSet<>(),
            new HashSet<>(),
            new HashSet<>());
    var pressMultipowerInclinado =
        new ExerciseEntity(
            null,
            "PRESS EN MULTIPOWER INCLINADO",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            multipower,
            new HashSet<>(List.of(chestUpper)),
            new HashSet<>(),
            new HashSet<>());
    var pressMultipowerDeclinado =
        new ExerciseEntity(
            null,
            "PRESS EN MULTIPOWER DECLINADO",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            multipower,
            new HashSet<>(List.of(chestLower)),
            new HashSet<>(),
            new HashSet<>());
    var aperturasMancuernas =
        new ExerciseEntity(
            null,
            "PRESS CON MANCUERNAS",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            dumbbell,
            new HashSet<>(List.of(chestMiddle)),
            new HashSet<>(),
            new HashSet<>());
    var aperturasMancuernasInclinado =
        new ExerciseEntity(
            null,
            "PRESS CON MANCUERNAS INCLINADO",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            dumbbell,
            new HashSet<>(List.of(chestUpper, chestMiddle)),
            new HashSet<>(),
            new HashSet<>());
    var aperturasMancuernasDeclinado =
        new ExerciseEntity(
            null,
            "PRESS CON MANCUERNAS DECLINADO",
            null,
            AccessTypeEnum.PUBLIC,
            adminId,
            false,
            dumbbell,
            new HashSet<>(List.of(chestLower, chestMiddle)),
            new HashSet<>(),
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
    log.info("\tInserting {} exercises...", exercises.size());
    exerciseDao.saveAll(exercises);
    log.info("\tInserting {} exercises", exercises.size());

    // ---------------------------------------------------------------------------------------------
    // ----------------------------------EXERCISES_MUSCLE_GROUPS------------------------------------
    // ---------------------------------------------------------------------------------------------
    var pressBancaChest = new MuscleGroupExerciseEntity(null, 1.0, chest, pressBanca);
    var pressBancaInclChest = new MuscleGroupExerciseEntity(null, 1.0, chest, pressBancaInclinado);
    var press1 = new MuscleGroupExerciseEntity(null, 1.0, chest, pressBancaDeclinado);
    var press2 = new MuscleGroupExerciseEntity(null, 1.0, chest, pressMancuernas);
    var press3 = new MuscleGroupExerciseEntity(null, 1.0, chest, pressMancuernasInclinado);
    var press4 = new MuscleGroupExerciseEntity(null, 1.0, chest, pressMancuernasDeclinado);
    var press5 = new MuscleGroupExerciseEntity(null, 1.0, chest, pressMultipower);
    var press6 = new MuscleGroupExerciseEntity(null, 1.0, chest, pressMultipowerInclinado);
    var press7 = new MuscleGroupExerciseEntity(null, 1.0, chest, pressMultipowerDeclinado);
    var press8 = new MuscleGroupExerciseEntity(null, 1.0, chest, aperturasMancuernas);
    var press9 = new MuscleGroupExerciseEntity(null, 1.0, chest, aperturasMancuernasInclinado);
    var press10 = new MuscleGroupExerciseEntity(null, 1.0, chest, aperturasMancuernasDeclinado);

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
    log.info("\tInserting {} exercises-muscleGroups...", exercisesMuscleGroups.size());
    muscleGroupExerciseDao.saveAll(exercisesMuscleGroups);
    log.info("\tInserting {} exercises-muscleGroups", exercisesMuscleGroups.size());

    populateExerciseUses();

    return loadTypes.size()
        + muscleSupGroups.size()
        + muscleGroups.size()
        + muscleSubGroups.size()
        + exercises.size()
        + exercisesMuscleGroups.size();
  }

  private void populateExerciseUses() {
    List<ExerciseEntity> exercises = exerciseDao.findAll();
    List<UserEntity> users = userDao.findAll();
    List<ExerciseUsesEntity> exerciseUses = new ArrayList<>();
    for (ExerciseEntity exercise : exercises) {
      for (UserEntity user : users) {
        int randomPositiveInt = ThreadLocalRandom.current().nextInt(0, 100);
        exerciseUses.add(new ExerciseUsesEntity(null, randomPositiveInt, user.getId(), exercise));
      }
    }
    log.info("\tInserting {} exerciseUses...", exerciseUses.size());
    exerciseUsesDao.saveAll(exerciseUses);
    log.info("\tInserting {} exerciseUses", exerciseUses.size());
  }
}
