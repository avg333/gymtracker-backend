package org.avillar.gymtracker.workoutapi.setgroup.createsetgroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.SetDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.SetGroupDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.WorkoutDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException;
import org.avillar.gymtracker.workoutapi.common.exception.application.ExerciseUnavailableException.AccessError;
import org.avillar.gymtracker.workoutapi.common.facade.exercise.ExercisesFacadeImpl;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@Execution(ExecutionMode.SAME_THREAD)
@Sql(
    scripts = "classpath:workoutapi/data.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class CreateSetGroupIT {

  private static final String PATH = "/api/v1/workouts/{workoutId}/setGroups";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String WORKOUT_ID = "f48ecca5-a840-4c2f-8788-f82dd9103239";

  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private SetGroupDao setGroupDao;
  @Autowired private SetDao setDao;

  @MockBean private ExercisesFacadeImpl exercisesFacade;

  private static JSONObject getJsonObject() throws JSONException {
    final JSONObject createSetRequestDto = new JSONObject();
    createSetRequestDto.put("description", "Description TEST");
    createSetRequestDto.put("exerciseId", UUID.randomUUID().toString());
    return createSetRequestDto;
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldCreateSetSuccessfullyAndUpdatedSetData() throws Exception {
    final JSONObject jsonObject = getJsonObject();

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    final int setGroupPosition =
        workoutDao
            .getWorkoutWithSetGroupsById((UUID.fromString(WORKOUT_ID)))
            .get()
            .getSetGroups()
            .size();

    doNothing()
        .when(exercisesFacade)
        .checkExerciseAccessById(UUID.fromString((String) jsonObject.get("exerciseId")));

    mockMvc
        .perform(
            post(PATH, WORKOUT_ID).contentType(APPLICATION_JSON).content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isString())
        .andExpect(jsonPath("$.listOrder").value(setGroupPosition))
        .andExpect(jsonPath("$.description").value(jsonObject.get("description")))
        .andExpect(jsonPath("$.exerciseId").value(jsonObject.get("exerciseId")))
        .andExpect(jsonPath("$.workout.id").value(WORKOUT_ID))
        .andExpect(jsonPath("$.sets").doesNotExist());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize + 1);
    assertThat(setDao.findAll()).hasSize(setsSize);

    final WorkoutEntity workoutEntityAfterSave =
        workoutDao.getWorkoutWithSetGroupsById(UUID.fromString(WORKOUT_ID)).get();
    assertThat(workoutEntityAfterSave.getSetGroups()).hasSize(setGroupPosition + 1);

    final SetGroupEntity createdSetGroup =
        workoutEntityAfterSave.getSetGroups().stream().toList().get(setGroupPosition);
    assertThat(createdSetGroup.getId()).isNotNull();
    assertThat(createdSetGroup.getListOrder()).isEqualTo(setGroupPosition);
    assertThat(createdSetGroup.getDescription()).isEqualTo(jsonObject.get("description"));
    assertThat(createdSetGroup.getExerciseId()).hasToString((String) jsonObject.get("exerciseId"));
    assertThat(createdSetGroup.getWorkout().getId().toString()).hasToString(WORKOUT_ID);
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotCreateSetGroupWhenExerciseIdIsNotValidAndReturnBadRequest() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    final JSONObject jsonObject = getJsonObject();
    final UUID exerciseId = UUID.fromString((String) jsonObject.get("exerciseId"));

    doThrow(new ExerciseUnavailableException(exerciseId, AccessError.NOT_FOUND))
        .when(exercisesFacade)
        .checkExerciseAccessById(exerciseId);

    mockMvc
        .perform(
            post(PATH, WORKOUT_ID).contentType(APPLICATION_JSON).content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("No exercise found with ID: " + exerciseId))
        .andExpect(jsonPath("$.validationErrors").isEmpty());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize);
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotCreateSetGroupWhenRequestIsNotValidAndReturnBadRequest() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    final JSONObject jsonObject = getJsonObject();
    jsonObject.put("description", "a".repeat(256));

    mockMvc
        .perform(
            post(PATH, WORKOUT_ID).contentType(APPLICATION_JSON).content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Invalid request content."))
        .andExpect(jsonPath("$.validationErrors").isArray())
        .andExpect(jsonPath("$.validationErrors[0].field").value("description"))
        .andExpect(jsonPath("$.validationErrors[0].message").isString());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize);
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldNotCreateSetGroupWhenUserHasNoAccessAndReturnForbidden() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    mockMvc
        .perform(
            post(PATH, WORKOUT_ID)
                .contentType(APPLICATION_JSON)
                .content(getJsonObject().toString()))
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

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotCreateSetGroupWhenWorkoutIsNotFoundAndReturnNotFound() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    mockMvc
        .perform(
            post(PATH, UUID.randomUUID())
                .contentType(APPLICATION_JSON)
                .content(getJsonObject().toString()))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Entity not found"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize);
  }
}
