package org.avillar.gymtracker.workoutapi;

import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.workoutapi.set.domain.Set;
import org.avillar.gymtracker.workoutapi.set.domain.SetDao;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.workout.domain.Workout;
import org.avillar.gymtracker.workoutapi.workout.domain.WorkoutDao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WorkoutsDataLoader implements ApplicationRunner {

  private static final int TOTAL_USERS = 3;
  private static final int TOTAL_DIAS = 90;
  private static final double PROB_GO_TO_THE_GIM = 0.7;
  private static final int MIN_EXERCISES = 3;
  private static final int MAX_EXERCISES = 6;
  private final Random random = new Random();
  private final SetGroupDao setGroupDao;
  private final SetDao setDao;
  private final WorkoutDao workoutDao;
  private final List<Workout> workouts = new ArrayList<>();
  private final List<SetGroup> setGroups = new ArrayList<>();
  private final List<Set> sets = new ArrayList<>();

  public void run(ApplicationArguments args) {
    if (!workoutDao.findAll().isEmpty()) {
      log.info("La base de datos ya tiene datos. No se insertaran mas");
    }
    createHeavyData();
    saveHeavyData();
  }

  private void saveHeavyData() {
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("Guardando " + workouts.size() + " workouts");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    workoutDao.saveAll(workouts);

    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("Guardando " + setGroups.size() + " setGroups");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    setGroupDao.saveAll(setGroups);

    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("Guardando " + sets.size() + " sets");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    setDao.saveAll(sets.subList(0, Math.min(sets.size(), 250000)));
    if (sets.size() > 250000) {
      setDao.saveAll(sets.subList(250000, Math.min(sets.size(), 500000)));
    }
    if (sets.size() > 500000) {
      setDao.saveAll(sets.subList(500000, Math.min(sets.size(), 750000)));
    }
    if (sets.size() > 750000) {
      setDao.saveAll(sets.subList(750000, Math.min(sets.size(), 1000000)));
    }
    if (sets.size() > 1000000) {
      setDao.saveAll(sets.subList(1000000, sets.size() - 1));
    }

    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("FINISH");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
    log.info("------------------------------------------------------");
  }

  private void createHeavyData() {
    var userIds = createUsers();
    int contador = 1;
    for (var userId : userIds) {
      createWorkoutsForUser(userId);
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("CREADO USUARIO " + contador++ + " de " + TOTAL_USERS);
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
      log.info("------------------------------------------------------");
    }
  }

  private void createWorkoutsForUser(UUID userId) {
    final int days = 35;
    final Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.DATE, -days - 1);
    for (int i = 0; i < TOTAL_DIAS; i++) {
      c.add(Calendar.DATE, 1);
      if (random.nextDouble() < PROB_GO_TO_THE_GIM) {
        createWorkout(userId, c.getTime());
      }
    }
  }

  private void createWorkout(UUID userId, Date date) {
    var workout = new Workout(date, "", userId, new HashSet<>());
    int totalExercises = MIN_EXERCISES + random.nextInt(MAX_EXERCISES - MIN_EXERCISES + 1);
    workouts.add(workout);
    for (int i = 0; i < totalExercises; i++) {
      createSetGroup(workout, i);
    }
  }

  private void createSetGroup(Workout workout, int listOrder) {
    var setGroup = new SetGroup(null, UUID.randomUUID(), workout, new HashSet<>());
    setGroup.setListOrder(listOrder);
    int totalSets = MIN_EXERCISES + random.nextInt(MAX_EXERCISES - MIN_EXERCISES + 1);
    setGroups.add(setGroup);
    for (int i = 0; i < totalSets; i++) {
      createSets(setGroup, i);
    }
  }

  private void createSets(SetGroup setGroup, int listOrder) {
    var set = new Set(null, 12, 12.0, 12.0, setGroup);
    set.setListOrder(listOrder);
    sets.add(set);
  }

  private List<UUID> createUsers() {
    final List<UUID> userIds = new ArrayList<>();
    for (int i = 0; i < TOTAL_USERS; i++) {
      userIds.add(UUID.randomUUID());
    }

    return userIds;
  }

  public void createAll(UUID uuid) {
    createWorkouts(uuid);
    createSets();
  }

  private void createWorkouts(final UUID userId) {
    final List<Workout> workouts = new ArrayList<>();
    final int days = 35;
    final Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.DATE, -days - 1);
    for (int i = 0; i < days; i++) {
      c.add(Calendar.DATE, 1);
      if (random.nextDouble() < 0.2) {
        continue;
      }
      workouts.add(new Workout(c.getTime(), null, userId, null));
    }
    workoutDao.saveAll(workouts);
  }

  private void createSets() {
    final List<Workout> workouts = workoutDao.findAll();
    final List<SetGroup> setGroups = new ArrayList<>();

    for (final Workout workout : workouts) {
      for (int i = 0; 5 > i; i++) {
        long rnd = random.nextLong();
        final SetGroup setGroup =
            new SetGroup(
                random.nextDouble() < 0.2 ? "WorkoutSetGroup" + rnd : null,
                UUID.randomUUID(),
                workout,
                null);
        setGroup.setListOrder(i);
        setGroups.add(setGroup);
      }
    }
    setGroupDao.saveAll(setGroups);

    final List<Set> sets = new ArrayList<>();
    for (final SetGroup setGroup : setGroups) {
      final int totalSeries = random.nextInt(2, 6);
      for (int i = 0; i < totalSeries; i++) {
        final int reps = random.nextInt(3, 15);
        final double rir = random.nextInt(0, 4);
        final double weight = Math.round((random.nextInt(5, 100)) * 100.0) / 100.0;
        final Set set =
            new Set(
                random.nextDouble() < 0.2 ? "SetDescription" : null, reps, rir, weight, setGroup);
        set.setListOrder(i);
        sets.add(set);
      }
    }

    setDao.saveAll(sets);
    log.info("Creados las sets");
  }
}
