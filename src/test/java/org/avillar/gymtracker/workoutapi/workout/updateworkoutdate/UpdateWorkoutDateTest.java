package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.IntegrationBaseTest;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

class UpdateWorkoutDateTest extends IntegrationBaseTest {

  final List<Workout> workouts = new ArrayList<>();
  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;

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
    workouts.clear();
    workouts.add(workout);
  }

  @AfterEach
  void afterEach() {
    workoutDao.deleteById(workouts.get(0).getId());
    workouts.clear();
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void deleteOneWorkout() throws Exception {
    final Workout workout = workouts.get(0);
    final UUID workoutId = workouts.get(0).getId();
    final String updateWorkoutDateRequest = """
{"date": "%s"}
""";

    mockMvc
        .perform(
            patch("/workout-api/workouts/" + workout.getId() + "/date")
                .contentType(APPLICATION_JSON)
                .content(String.format(updateWorkoutDateRequest, "2023-06-28")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.date").value("2023-06-28"));

    final Optional<Workout> workoutDb = workoutDao.findById(workoutId);
    Assertions.assertTrue(workoutDb.isPresent());
    // Assertions.assertEquals("newDescription", workoutDb.get().getDescription());
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void deleteOneWorkout2() throws Exception {
    final Workout workout = workouts.get(0);
    final String updateWorkoutDateRequest = """
{"description": null}
""";

    mockMvc
        .perform(
            patch("/workout-api/workouts/" + workout.getId() + "/date")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void deleteNotFoundAndNotPermission() throws Exception {
    final Workout workout = workouts.get(0);
    final String updateWorkoutDateRequest = """
{"description": "newDescription"}
""";
    mockMvc
        .perform(
            patch("/workout-api/workouts/" + UUID.randomUUID() + "/description")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest))
        .andDo(print())
        .andExpect(status().isNotFound());
    mockMvc
        .perform(
            patch("/workout-api/workouts/" + workout.getId() + "/description")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
}
