package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.IntegrationBaseTest;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

class GetWorkoutWithSetGroupsTest extends IntegrationBaseTest {

  final List<Workout> workouts = new ArrayList<>();
  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private UserDao userDao;

  @BeforeAll
  public void beforeAll() {
    this.startRedis();
    this.createUsers();
  }

  @AfterAll
  public void afterAll() {
    this.stopRedis();
    this.deleteUsers();
  }

  @BeforeEach
  void beforeEach() {
    final UserApp userApp = userDao.findByUsername(USER_NAME_OK);

    final Workout workout = new Workout(new Date(), null, userApp.getId(), new HashSet<>());
    workoutDao.save(workout);
    workouts.add(workout);
  }

  @AfterEach
  void afterEach() {
    workoutDao.deleteById(workouts.get(0).getId());
    workouts.clear();
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void getOneWorkout() throws Exception {
    final Workout workout = workouts.get(0);
    final UUID workoutId = workouts.get(0).getId();

    mockMvc
        .perform(get("/workout-api/workouts/" + workout.getId()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(workoutId.toString()));
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void getNotFoundAndNotPermission() throws Exception {
    final Workout workout = workouts.get(0);
    final UUID workoutId = workouts.get(0).getId();
    mockMvc
        .perform(get("/workout-api/workouts/" + UUID.randomUUID()))
        .andDo(print())
        .andExpect(status().isNotFound());
    mockMvc
        .perform(get("/workout-api/workouts/" + workoutId))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
}
