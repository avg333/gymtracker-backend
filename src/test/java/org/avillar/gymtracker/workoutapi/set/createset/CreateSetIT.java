package org.avillar.gymtracker.workoutapi.set.createset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.SetDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.SetGroupDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.WorkoutDao;
import org.hamcrest.Matchers;
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

@Execution(ExecutionMode.SAME_THREAD)
@Sql(
    scripts = "classpath:workoutapi/data.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class CreateSetIT {

  private static final String PATH = "/api/v1/setGroups/{setGroupId}/sets";
  private static final String ISO_8601_REGEX = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}$";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String SET_GROUP_ID = "7a8f6de6-7c6d-466e-bacb-9dcb26bd78ad";

  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private SetGroupDao setGroupDao;
  @Autowired private SetDao setDao;

  private static JSONObject getJsonObject() throws JSONException {
    final JSONObject createSetRequestDto = new JSONObject();
    createSetRequestDto.put("description", "Description TEST");
    createSetRequestDto.put("reps", 10);
    createSetRequestDto.put("rir", 8.0);
    createSetRequestDto.put("weight", 100.0);
    createSetRequestDto.put("completed", true);
    return createSetRequestDto;
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldCreateSetSuccessfullyAndReturnSet() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    final int setGroupSetsSize =
        setGroupDao
            .getSetGroupFullByIds(Set.of(UUID.fromString(SET_GROUP_ID)))
            .get(0)
            .getSets()
            .size();

    final JSONObject jsonObject = getJsonObject();
    final Date now = new Date();
    mockMvc
        .perform(
            post(PATH, SET_GROUP_ID).contentType(APPLICATION_JSON).content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").isString())
        .andExpect(jsonPath("$.listOrder").value(setGroupSetsSize))
        .andExpect(jsonPath("$.description").value(jsonObject.get("description")))
        .andExpect(jsonPath("$.reps").value(jsonObject.get("reps")))
        .andExpect(jsonPath("$.rir").value(jsonObject.get("rir")))
        .andExpect(jsonPath("$.weight").value(jsonObject.get("weight")))
        .andExpect(jsonPath("$.completedAt", Matchers.matchesPattern(ISO_8601_REGEX)))
        .andExpect(jsonPath("$.setGroup.id").value(SET_GROUP_ID));

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize + 1);

    final SetGroupEntity setGroupEntityAfterSave =
        setGroupDao.getSetGroupFullByIds(Set.of(UUID.fromString(SET_GROUP_ID))).get(0);
    assertThat(setGroupEntityAfterSave.getSets()).hasSize(setGroupSetsSize + 1);

    final SetEntity createdSet =
        setGroupEntityAfterSave.getSets().stream().toList().get(setGroupSetsSize);
    assertThat(createdSet.getId()).isNotNull();
    assertThat(createdSet.getListOrder()).isEqualTo(setGroupSetsSize);
    assertThat(createdSet.getDescription()).isEqualTo(jsonObject.get("description"));
    assertThat(createdSet.getReps()).isEqualTo(jsonObject.get("reps"));
    assertThat(createdSet.getRir()).isEqualTo(jsonObject.get("rir"));
    assertThat(createdSet.getWeight()).isEqualTo(jsonObject.get("weight"));
    assertThat(createdSet.getCompletedAt())
        .isNotNull()
        .isCloseTo(now, TimeUnit.SECONDS.toMillis(5));
    assertThat(createdSet.getSetGroup().getId().toString()).hasToString(SET_GROUP_ID);
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotCreateSetWhenRequestIsNotValidAndReturnBadRequest() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    final JSONObject jsonObject = getJsonObject();
    jsonObject.put("rir", -1.0);

    mockMvc
        .perform(
            post(PATH, SET_GROUP_ID).contentType(APPLICATION_JSON).content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Invalid request content."))
        .andExpect(jsonPath("$.validationErrors").isArray())
        .andExpect(jsonPath("$.validationErrors[0].field").value("rir"))
        .andExpect(jsonPath("$.validationErrors[0].message").isString());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize);
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldNotCreateSetWhenUserHasNoAccessAndReturnForbidden() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    mockMvc
        .perform(
            post(PATH, SET_GROUP_ID)
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
  void shouldNotCreateSetWhenSetGroupIsNotFoundAndReturnNotFound() throws Exception {

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
