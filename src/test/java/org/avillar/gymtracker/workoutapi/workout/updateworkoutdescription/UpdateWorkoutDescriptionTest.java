package org.avillar.gymtracker.workoutapi.workout.updateworkoutdescription;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserApp.ActivityLevelEnum;
import org.avillar.gymtracker.authapi.domain.UserApp.GenderEnum;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.avillar.gymtracker.workoutapi.IntegrationTestDataGenerator;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class UpdateWorkoutDescriptionTest {

  private static final String USER_NAME_OK = "adrian";
  private static final String USER_NAME_KO = "chema";

  private IntegrationTestDataGenerator integrationTestDataGenerator;

  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private UserDao userDao;


  @BeforeAll
  public void before() {
    userDao.deleteAll();
    userDao.saveAll(
        List.of(
            new UserApp(
                null,
                "chema",
                new BCryptPasswordEncoder().encode("chema69"),
                null,
                "Chema",
                "Garcia",
                "Romero",
                null,
                GenderEnum.MALE,
                ActivityLevelEnum.EXTREME),
            new UserApp(
                null,
                "alex",
                new BCryptPasswordEncoder().encode("alex69"),
                null,
                "Alex",
                "Garcia",
                "Fernandez",
                null,
                GenderEnum.FEMALE,
                ActivityLevelEnum.SEDENTARY),
            new UserApp(
                null,
                "adrian",
                new BCryptPasswordEncoder().encode("adrian69"),
                null,
                "Adrian",
                "Villar",
                "Gesto",
                null,
                GenderEnum.MALE,
                ActivityLevelEnum.MODERATE)));
  }

  @AfterAll
  public void afterAll() {
    userDao.deleteAll();
    workoutDao.deleteAll();
  }

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
  void updateDescriptionOK() throws Exception {
    final Workout workout = integrationTestDataGenerator.getWorkouts().get(0);

    final JSONObject updateWorkoutDateRequest = new JSONObject();
    updateWorkoutDateRequest.put("description", "Description TEST");

    mockMvc
        .perform(
            patch("/workout-api/workouts/" + workout.getId() + "/description")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.description").value("Description TEST"));
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void updateDescriptionSameDescription() throws Exception {
    final Workout workout = integrationTestDataGenerator.getWorkouts().get(0);

    final JSONObject updateWorkoutDateRequest = new JSONObject();
    updateWorkoutDateRequest.put("description", workout.getDescription());

    mockMvc
        .perform(
            patch("/workout-api/workouts/" + workout.getId() + "/description")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.description").value(workout.getDescription()));
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void updateDescriptionBadRequest() throws Exception {
    final Workout workout = integrationTestDataGenerator.getWorkouts().get(0);

    final JSONObject updateWorkoutDateRequest = new JSONObject();
    updateWorkoutDateRequest.put("description", new String(new char[300]).replace('\0', ' '));

    mockMvc
        .perform(
            patch("/workout-api/workouts/" + workout.getId() + "/description")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void updateDescriptionNotFound() throws Exception {
    final JSONObject updateWorkoutDateRequest = new JSONObject();
    updateWorkoutDateRequest.put("description", "Description TEST");

    mockMvc
        .perform(
            patch("/workout-api/workouts/" + UUID.randomUUID() + "/description")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest.toString()))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void updateDescriptionNotPermission() throws Exception {
    final Workout workout = integrationTestDataGenerator.getWorkouts().get(0);

    final JSONObject updateWorkoutDateRequest = new JSONObject();
    updateWorkoutDateRequest.put("description", "Description TEST");

    mockMvc
        .perform(
            patch("/workout-api/workouts/" + workout.getId() + "/description")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest.toString()))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
}
