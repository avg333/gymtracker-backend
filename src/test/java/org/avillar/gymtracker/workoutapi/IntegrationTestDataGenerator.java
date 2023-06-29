package org.avillar.gymtracker.workoutapi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.Workout;

@Getter
public class IntegrationTestDataGenerator {
  private final List<Workout> workouts = new ArrayList<>();
  private final List<SetGroup> setGroups = new ArrayList<>();
  private final List<Set> sets = new ArrayList<>();

  private final UUID userId;

  public IntegrationTestDataGenerator(
      final UUID userId,
      final int workoustNumber,
      final int setGroupsNumber,
      final int setsNumber) {
    this.userId = userId;

    generateWorkouts(workoustNumber);
    generateSetGroups(setGroupsNumber);
    generateSets(setsNumber);
  }

  private void generateWorkouts(final int workoustNumber) {
    final Calendar c = Calendar.getInstance();
    c.setTime(new Date());
    for (int i = 0; i < workoustNumber; i++) {
      c.add(Calendar.DATE, 1);
      final Workout workout = new Workout();
      workout.setUserId(userId);
      workout.setDate(c.getTime());
      workouts.add(workout);
    }
  }

  private void generateSetGroups(final int setGroupsNumber) {
    for (final Workout workout : workouts) {
      for (int i = 0; i < setGroupsNumber; i++) {
        final SetGroup setGroup = new SetGroup();
        setGroup.setListOrder(i);
        setGroup.setExerciseId(UUID.randomUUID());
        setGroup.setWorkout(workout);
        setGroups.add(setGroup);
      }
    }
  }

  private void generateSets(final int setsNumber) {
    for (final SetGroup setGroup : setGroups) {
      for (int i = 0; i < setsNumber; i++) {
        final Set set = new Set();
        set.setListOrder(i);
        set.setReps(1);
        set.setRir(1.0);
        set.setWeight(1.0);
        set.setSetGroup(setGroup);
        sets.add(set);
      }
    }
  }
}
