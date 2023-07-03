package org.avillar.gymtracker.workoutsapi.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.avillar.gymtracker.workoutapi.workout.createworkout.infrastructure.model.CreateWorkoutResponseInfrastructure;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class CreateWorkoutTest {

  private static final String USER_NAME_OK = "adrian";
  private static final String USER_NAME_KO = "chema";
  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private SetGroupDao setGroupDao;
  @Autowired private SetDao setDao;
  @Autowired private UserDao userDao;

  @AfterEach
  void afterEach() {
    workoutDao.deleteAll();
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void createOneWorkoutOk() throws Exception {
    final UserApp userApp = userDao.findByUsername(USER_NAME_OK);
    final JSONObject updateWorkoutDateRequest = new JSONObject();
    updateWorkoutDateRequest.put("date", "2022-03-03");
    updateWorkoutDateRequest.put("description", "Description TEST");

    final ResultActions resultActions =
        mockMvc
            .perform(
                post("/org.avillar.gymtracker.workoutapi.workout-api/users/" + userApp.getId() + "/workouts")
                    .contentType(APPLICATION_JSON)
                    .content(updateWorkoutDateRequest.toString()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.userId").value(userApp.getId().toString()))
            .andExpect(jsonPath("$.date").value(updateWorkoutDateRequest.get("date")))
            .andExpect(
                jsonPath("$.description").value(updateWorkoutDateRequest.get("description")));

    final CreateWorkoutResponseInfrastructure result =
        new ObjectMapper()
            .readValue(
                resultActions.andReturn().getResponse().getContentAsString(),
                CreateWorkoutResponseInfrastructure.class);

    final Optional<Workout> workoutDb = workoutDao.findById(result.getId());
    assertTrue(workoutDb.isPresent());
    assertEquals(userApp.getId(), result.getUserId());
    assertEquals(updateWorkoutDateRequest.get("description"), result.getDescription());
    // assertEquals(updateWorkoutDateRequest.get("date"), result.getDate());TODO Verificar date
    assertEquals(1, workoutDao.findAll().size());
    assertTrue(setGroupDao.findAll().isEmpty());
    assertTrue(setDao.findAll().isEmpty());
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void createTwoWorkoutsInTheSameDate() throws Exception {
    final UserApp userApp = userDao.findByUsername(USER_NAME_OK);
    final JSONObject updateWorkoutDateRequest = new JSONObject();
    updateWorkoutDateRequest.put("date", "2022-03-03");
    updateWorkoutDateRequest.put("description", "Description TEST");

    mockMvc
        .perform(
            post("/org.avillar.gymtracker.workoutapi.workout-api/users/" + userApp.getId() + "/workouts")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.userId").value(userApp.getId().toString()))
        .andExpect(jsonPath("$.date").value(updateWorkoutDateRequest.get("date")))
        .andExpect(jsonPath("$.description").value(updateWorkoutDateRequest.get("description")));

    mockMvc
        .perform(
            post("/org.avillar.gymtracker.workoutapi.workout-api/users/" + userApp.getId() + "/workouts")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest());

    assertEquals(1, workoutDao.findAll().size());
    assertTrue(setGroupDao.findAll().isEmpty());
    assertTrue(setDao.findAll().isEmpty());
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void createOneInOtherUser() throws Exception {
    final UserApp userApp = userDao.findByUsername(USER_NAME_OK);
    final JSONObject updateWorkoutDateRequest = new JSONObject();
    updateWorkoutDateRequest.put("date", "2022-03-03");
    updateWorkoutDateRequest.put("description", "Description TEST");

    mockMvc
        .perform(
            post("/org.avillar.gymtracker.workoutapi.workout-api/users/" + userApp.getId() + "/workouts")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest.toString()))
        .andDo(print())
        .andExpect(status().isForbidden());

    assertTrue(workoutDao.findAll().isEmpty());
    assertTrue(setGroupDao.findAll().isEmpty());
    assertTrue(setDao.findAll().isEmpty());
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void createOneWorkoutBadRequest() throws Exception {
    final UserApp userApp = userDao.findByUsername(USER_NAME_OK);

    final JSONObject updateWorkoutDateRequest = new JSONObject();
    updateWorkoutDateRequest.put("date", null);
    updateWorkoutDateRequest.put("description", "Description TEST");
    mockMvc
        .perform(
            post("/org.avillar.gymtracker.workoutapi.workout-api/users/" + userApp.getId() + "/workouts")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest());

    updateWorkoutDateRequest.put("date", "2022-03-03");
    updateWorkoutDateRequest.put("description", new String(new char[300]).replace('\0', ' '));
    mockMvc
        .perform(
            post("/org.avillar.gymtracker.workoutapi.workout-api/users/" + userApp.getId() + "/workouts")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest());

    assertTrue(workoutDao.findAll().isEmpty());
    assertTrue(setGroupDao.findAll().isEmpty());
    assertTrue(setDao.findAll().isEmpty());
  }
}
