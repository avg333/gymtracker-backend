package org.avillar.gymtracker.workoutapi.workout.getworkout;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.avillar.gymtracker.authapi.auth.domain.UserApp;
import org.avillar.gymtracker.authapi.auth.domain.UserDao;
import org.avillar.gymtracker.workoutapi.IntegrationTestDataGenerator;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class GetWorkoutTest {

  private static final String USER_NAME_OK = "adrian";
  private static final String USER_NAME_KO = "chema";

  private IntegrationTestDataGenerator integrationTestDataGenerator;

  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private UserDao userDao;

  @BeforeEach
  void beforeEach() {
    final UserApp userApp = userDao.findByUsername(USER_NAME_OK);

    integrationTestDataGenerator = new IntegrationTestDataGenerator(userApp.getId(), 1, 0, 0);
    workoutDao.save(integrationTestDataGenerator.getWorkouts().get(0));
  }

  @AfterEach
  void afterEach() {
    workoutDao.deleteAll();
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void getOneWorkout() throws Exception {
    final Workout workout = integrationTestDataGenerator.getWorkouts().get(0);

    mockMvc
        .perform(get("/org.avillar.gymtracker.workoutapi.workout-api/workouts/" + workout.getId()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(workout.getId().toString()))
        // .andExpect(jsonPath("$.date").value(org.avillar.gymtracker.workoutapi.workout.getId().toString()))FIXME
        .andExpect(jsonPath("$.description").value(workout.getDescription()))
        .andExpect(jsonPath("$.userId").value(workout.getUserId().toString()));
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void getNotFound() throws Exception {
    mockMvc
        .perform(get("/org.avillar.gymtracker.workoutapi.workout-api/workouts/" + UUID.randomUUID()))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void getNotPermission() throws Exception {
    final Workout workout = integrationTestDataGenerator.getWorkouts().get(0);
    mockMvc
        .perform(get("/org.avillar.gymtracker.workoutapi.workout-api/workouts/" + workout.getId()))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
}
