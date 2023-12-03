package org.avillar.gymtracker.workoutapi;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.common.adapter.repository.UserDao;
import org.avillar.gymtracker.authapi.common.adapter.repository.model.UserEntity;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.ExerciseDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.ExerciseEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.SetDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.SetGroupDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.WorkoutDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
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
    var userIds = userDao.findAll().stream().map(UserEntity::getId).toList();
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
    exerciseIds.addAll(exerciseDao.findAll().stream().map(ExerciseEntity::getId).toList());
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
    c.setTime(new java.util.Date());
    c.add(Calendar.DATE, -days - 1);
    for (int i = 0; i < TOTAL_DIAS; i++) {
      c.add(Calendar.DATE, 1);
      if (random.nextDouble() < PROB_GO_TO_THE_GIM) {
        createWorkout(userId, new Date(c.getTime().getTime()), saveData);
      }
    }
  }

  private void createWorkout(UUID userId, Date date, SaveData saveData) {
    var workout = new WorkoutEntity(null, date, null, userId, new HashSet<>());
    int totalExercises = MIN_EXERCISES + random.nextInt(MAX_EXERCISES - MIN_EXERCISES + 1);
    saveData.getWorkouts().add(workout);
    for (int i = 0; i < totalExercises; i++) {
      createSetGroup(workout, i, saveData);
    }
  }

  private void createSetGroup(WorkoutEntity workout, int listOrder, SaveData saveData) {
    UUID exerciseId =
        exerciseIds.isEmpty()
            ? UUID.randomUUID()
            : exerciseIds.get(random.nextInt(exerciseIds.size()));
    var setGroup =
        new SetGroupEntity(
            null, listOrder, null, exerciseId, workout, new HashSet<>(), null, 3, 0, 1, 0, false);
    int totalSets = MIN_EXERCISES + random.nextInt(MAX_EXERCISES - MIN_EXERCISES + 1);
    saveData.getSetGroups().add(setGroup);
    var setRecords = generateSets(totalSets);
    for (int i = 0; i < totalSets; i++) {
      createSet(setGroup, i, saveData, setRecords.get(i));
    }
  }

  private void createSet(
      SetGroupEntity setGroup, int listOrder, SaveData saveData, SetRecord setRecord) {
    int reps = 3 + setRecord.reps();
    double rir = (int) setRecord.rir();
    double weight = (int) setRecord.weight();
    var set =
        new SetEntity(
            null,
            listOrder,
            null,
            reps,
            rir,
            weight,
            new Timestamp(new java.util.Date().getTime()),
            setGroup);
    saveData.getSets().add(set);
  }

  public List<SetRecord> generateSets(int numberOfSets) {
    List<SetRecord> sets = new ArrayList<>();

    for (int i = 0; i < numberOfSets; i++) {
      int reps = i == 0 ? random.nextInt(10) + 1 : sets.get(i - 1).reps() + random.nextInt(3) - 1;
      reps = reps <= 0 ? 1 : reps;

      double weight =
          i == 0
              ? random.nextDouble() * 100
              : sets.get(i - 1).weight() + (random.nextDouble() - 0.5) * 5;
      weight = weight < 0 ? 0 : weight;

      double previousRir = i == 0 ? random.nextDouble() * 5 : sets.get(i - 1).rir();
      double rir = previousRir + (Math.random() - 0.5);

      sets.add(new SetRecord(reps, weight, rir));
    }

    return sets;
  }

  @Data
  public static class SaveData {
    private final List<WorkoutEntity> workouts = new ArrayList<>();
    private final List<SetGroupEntity> setGroups = new ArrayList<>();
    private final List<SetEntity> sets = new ArrayList<>();
  }

  public record SetRecord(int reps, double weight, double rir) {}
}
