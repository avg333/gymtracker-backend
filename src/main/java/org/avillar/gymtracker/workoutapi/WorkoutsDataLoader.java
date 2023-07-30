package org.avillar.gymtracker.workoutapi;

import java.util.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.avillar.gymtracker.exercisesapi.domain.ExerciseDao;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "${workoutsApiPrefix}/")
public class WorkoutsDataLoader implements ApplicationRunner {

  private static final int TOTAL_DIAS = 365;
  private static final double PROB_GO_TO_THE_GIM = 0.7;
  private static final int MIN_EXERCISES = 3;
  private static final int MAX_EXERCISES = 6;
  private final Random random = new Random();
  private final UserDao userDao;
  private final ExerciseDao exerciseDao;
  private final List<UUID> exerciseIds = new ArrayList<>();
  private final SetGroupDao setGroupDao;
  private final SetDao setDao;
  private final WorkoutDao workoutDao;


  @Value("${spring.profiles.active}")
  private String activeProfile;

  @PostMapping("/users/{userId}/create")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Void postWorkout(@PathVariable final UUID userId) {
    saveHeavyData(createHeavyData(List.of(userId)));
    return null;
  }

  public void run(ApplicationArguments args) {
    final long start = System.currentTimeMillis();
    if (activeProfile.equals("test")) {
      return;
    } else if (!workoutDao.findAll().isEmpty()) {
      log.info("Micro workouts is already populated");
      return;
    }

    log.info("Populating workouts micro...");
    var userIds = userDao.findAll().stream().map(UserApp::getId).toList();
    final SaveData saveData = createHeavyData(userIds);
    saveHeavyData(saveData);
    int totalInserts =
        saveData.getWorkouts().size() + saveData.getSetGroups().size() + saveData.getSets().size();

    long finish = System.currentTimeMillis();
    log.info("Populated workouts micro with {} entities in {} ms", totalInserts, finish - start);
  }

  private void saveHeavyData(final SaveData saveData) {
    log.info("\tInserting {} workouts...", saveData.getWorkouts().size());
    workoutDao.saveAll(saveData.getWorkouts());
    log.info("\tInserted {} workouts", saveData.getWorkouts().size());

    log.info("\tInserting {} setGroups...", saveData.getSetGroups().size());
    setGroupDao.saveAll(saveData.getSetGroups());
    log.info("\tInserted {} setGroups", saveData.getSetGroups().size());

    log.info("\tInserting {} sets...", saveData.getSets().size());
    setDao.saveAll(saveData.getSets());
    log.info("\tInserted {} sets", saveData.getSets().size());
  }

  private SaveData createHeavyData(final List<UUID> userIds) {
    final SaveData saveData = new SaveData();
    exerciseIds.addAll(exerciseDao.findAll().stream().map(Exercise::getId).toList());
    log.info("\tCreating workouts for {} users...", userIds.size());
    for (var userId : userIds) {
      createWorkoutsForUser(userId, saveData);
    }
    log.info("\tCreated {} users", userIds.size());

    return saveData;
  }

  private void createWorkoutsForUser(UUID userId, SaveData saveData) {
    final int days = 35;
    final Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    c.add(Calendar.DATE, -days - 1);
    for (int i = 0; i < TOTAL_DIAS; i++) {
      c.add(Calendar.DATE, 1);
      if (random.nextDouble() < PROB_GO_TO_THE_GIM) {
        createWorkout(userId, c.getTime(), saveData);
      }
    }
  }

  private void createWorkout(UUID userId, Date date, SaveData saveData) {
    var workout = new Workout(date, "", userId, new HashSet<>());
    int totalExercises = MIN_EXERCISES + random.nextInt(MAX_EXERCISES - MIN_EXERCISES + 1);
    saveData.getWorkouts().add(workout);
    for (int i = 0; i < totalExercises; i++) {
      createSetGroup(workout, i, saveData);
    }
  }

  private void createSetGroup(Workout workout, int listOrder, SaveData saveData) {
    UUID exerciseId =
        exerciseIds.isEmpty()
            ? UUID.randomUUID()
            : exerciseIds.get(random.nextInt(exerciseIds.size()));
    var setGroup = new SetGroup(null, exerciseId, workout, new HashSet<>());
    setGroup.setListOrder(listOrder);
    int totalSets = MIN_EXERCISES + random.nextInt(MAX_EXERCISES - MIN_EXERCISES + 1);
    saveData.getSetGroups().add(setGroup);
    for (int i = 0; i < totalSets; i++) {
      createSet(setGroup, i, saveData);
    }
  }

  private void createSet(SetGroup setGroup, int listOrder, SaveData saveData) {
    int reps = 3 + random.nextInt(18 - 3 + 1);
    int rir = random.nextInt(3 + 1);
    int weight = 10 + random.nextInt(150 - 10 + 1);
    var set = new Set(null, reps, (double) rir, (double) weight, setGroup);
    set.setListOrder(listOrder);
    saveData.getSets().add(set);
  }

  @Data
  public static class SaveData {
    private final List<Workout> workouts = new ArrayList<>();
    private final List<SetGroup> setGroups = new ArrayList<>();
    private final List<Set> sets = new ArrayList<>();
  }
}
