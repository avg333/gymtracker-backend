package org.avillar.gymtracker.workoutapi.workout.createworkout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.SetDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.SetGroupDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.WorkoutDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@Execution(ExecutionMode.SAME_THREAD)
@Sql(
    scripts = "classpath:workoutapi/data.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class CreateWorkoutIT {

  private static final String PATH = "/api/v1/users/{userId}/workouts";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String USER_ID = "e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65";
  private static final String DATE_OK = "2023-10-30";
  private static final String DATE_KO = "2023-11-04";

  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private SetGroupDao setGroupDao;
  @Autowired private SetDao setDao;

  private static JSONObject getJsonObject() throws JSONException {
    final JSONObject createSetRequestDto = new JSONObject();
    createSetRequestDto.put("description", "Description TEST");
    createSetRequestDto.put("date", DATE_OK);
    return createSetRequestDto;
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldCreateWorkoutSuccessfullyAndReturnWorkout() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    final JSONObject jsonObject = getJsonObject();

    final ResultActions resultActions =
        mockMvc
            .perform(
                post(PATH, USER_ID)
                    .contentType(APPLICATION_JSON)
                    .content(getJsonObject().toString()))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").isString())
            .andExpect(jsonPath("$.date").value(jsonObject.get("date")))
            .andExpect(jsonPath("$.description").value(jsonObject.get("description")))
            .andExpect(jsonPath("$.userId").value(USER_ID));

    assertThat(workoutDao.findAll()).hasSize(workoutsSize + 1);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize);

    final JSONObject jsonResponse =
        new JSONObject(resultActions.andReturn().getResponse().getContentAsString());

    final Optional<WorkoutEntity> workoutEntityOptional =
        workoutDao.findById((UUID.fromString(jsonResponse.getString("id"))));
    assertThat(workoutEntityOptional).isPresent();
    final WorkoutEntity workoutEntity = workoutEntityOptional.get();
    // FIXME assertThat(workoutEntity.getDate()).hasToString((String) jsonObject.get("date"));
    assertThat(workoutEntity.getDescription()).isEqualTo(jsonObject.get("description"));
    assertThat(workoutEntity.getUserId()).isEqualTo(UUID.fromString(USER_ID));
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotCreateWorkoutWhenAlreadyExistsOneInThatDayAndReturnBadRequest() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    final JSONObject jsonObject = getJsonObject();
    jsonObject.put("date", DATE_KO);

    mockMvc
        .perform(post(PATH, USER_ID).contentType(APPLICATION_JSON).content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(
            jsonPath("$.message").value("There is already a workout on that date for that user"));

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize);
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotCreateWorkoutWhenRequestIsNotValidAndReturnBadRequest() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    final JSONObject jsonObject = getJsonObject();
    jsonObject.remove("date");

    mockMvc
        .perform(post(PATH, USER_ID).contentType(APPLICATION_JSON).content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Invalid request content."))
        .andExpect(jsonPath("$.validationErrors").isArray())
        .andExpect(jsonPath("$.validationErrors[0].field").value("date"))
        .andExpect(jsonPath("$.validationErrors[0].message").isString());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize);
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldNotCreateWorkoutWhenUserHasNoAccessAndReturnForbidden() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    mockMvc
        .perform(
            post(PATH, USER_ID).contentType(APPLICATION_JSON).content(getJsonObject().toString()))
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(
            jsonPath("$.message").value("You do not have permissions to access the resource"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize);
  }
}
