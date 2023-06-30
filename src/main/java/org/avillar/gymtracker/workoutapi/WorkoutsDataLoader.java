package org.avillar.gymtracker.workoutapi;

import java.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.auth.domain.UserApp;
import org.avillar.gymtracker.authapi.auth.domain.UserDao;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${workoutsApiPrefix}/")
public class WorkoutsDataLoader implements ApplicationRunner {

  private static final int TOTAL_USERS = 5;
  private static final int TOTAL_DIAS = 365;
  private static final double PROB_GO_TO_THE_GIM = 0.7;
  private static final int MIN_EXERCISES = 3;
  private static final int MAX_EXERCISES = 6;

  private static final int MAX_INSERTS = 250000;
  private final Random random = new Random();

  private final UserDao userDao;
  private final ExerciseDao exerciseDao;
  private final List<UUID> exerciseIds = new ArrayList<>();

  private final SetGroupDao setGroupDao;
  private final SetDao setDao;
  private final WorkoutDao workoutDao;
  private final List<Workout> workouts = new ArrayList<>();
  private final List<SetGroup> setGroups = new ArrayList<>();
  private final List<Set> sets = new ArrayList<>();

  @PostMapping("/users/{userId}/create")
  public ResponseEntity<Void> postWorkout(@PathVariable final UUID userId) {
    this.createAll(userId);
    return ResponseEntity.noContent().build();
  }

  public void run(ApplicationArguments args) {
    final long start = System.currentTimeMillis();
    if (workoutDao.findAll().isEmpty()) {
      log.info("Micro workouts is already populated");
      return;
    }
    log.info("Populating workouts micro...");

    createHeavyData();
    saveHeavyData();
    int totalInserts = workouts.size() + setGroups.size() + sets.size();
    long finish = System.currentTimeMillis();
    log.info(
        "Populated workouts micro with "
            + totalInserts
            + " enitities in "
            + (finish - start)
            + "ms");
  }

  private void saveHeavyData() {
    log.info("\tInserting " + workouts.size() + " workouts...");
    workoutDao.saveAll(workouts);
    log.info("\tInserted " + workouts.size() + " workouts");

    log.info("\tInserting " + setGroups.size() + " setGroups...");
    setGroupDao.saveAll(setGroups);
    log.info("\tInserted " + setGroups.size() + " setGroups");

    log.info("\tInserting " + sets.size() + " sets...");
    for (int i = 0; i < sets.size(); i += MAX_INSERTS) {
      saveSets(sets, i, i + MAX_INSERTS);
    }
    log.info("\tInserted " + sets.size() + " sets");
  }

  private void saveSets(final List<Set> sets, int min, int max) {
    log.info("\t\tInserting(sub) " + sets.size() + " sets...");
    setDao.saveAll(sets.subList(min, Math.min(sets.size(), max)));
    log.info("\t\tInserted(sub) " + sets.size() + " sets");
  }

  private void createHeavyData() {
    var userIds = userDao.findAll().stream().map(UserApp::getId).toList();
    exerciseIds.addAll(exerciseDao.findAll().stream().map(Exercise::getId).toList());
    // var userIds = createUsers();
    log.info("\tCreating " + TOTAL_USERS + " users...");
    for (var userId : userIds) {
      createWorkoutsForUser(userId);
    }
    log.info("\tCreated " + TOTAL_USERS + " users");
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
    var setGroup =
        new SetGroup(
            null, exerciseIds.get(random.nextInt(exerciseIds.size())), workout, new HashSet<>());
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
