package org.avillar.gymtracker.workoutapi.set.updatesetdata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.SetDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
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
class UpdateSetDataIT {

  private static final String PATH = "/api/v1/sets/{setId}";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String SET_ID = "c282a196-69f4-4de2-94fa-a60ec5201686";

  @Autowired private MockMvc mockMvc;
  @Autowired private SetDao setDao;

  private static JSONObject getJsonObject() throws JSONException {
    final JSONObject updateSetDataRequestDto = new JSONObject();
    updateSetDataRequestDto.put("description", "Description TEST");
    updateSetDataRequestDto.put("reps", 10);
    updateSetDataRequestDto.put("rir", 8.0);
    updateSetDataRequestDto.put("weight", 100.0);
    updateSetDataRequestDto.put("completed", false);
    return updateSetDataRequestDto;
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldUpdateSetDataSuccessfullyAndReturnSetData() throws Exception {
    final JSONObject jsonObject = getJsonObject();

    mockMvc
        .perform(patch(PATH, SET_ID).contentType(APPLICATION_JSON).content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.description").value(jsonObject.get("description")))
        .andExpect(jsonPath("$.reps").value(jsonObject.get("reps")))
        .andExpect(jsonPath("$.rir").value(jsonObject.get("rir")))
        .andExpect(jsonPath("$.weight").value(jsonObject.get("weight")))
        .andExpect(jsonPath("$.completedAt").isEmpty());

    final Optional<SetEntity> optionalSetEntity = setDao.findById(UUID.fromString(SET_ID));
    assertThat(optionalSetEntity).isPresent();
    final SetEntity updatedSet = optionalSetEntity.get();
    assertThat(updatedSet.getDescription()).isEqualTo(jsonObject.get("description"));
    assertThat(updatedSet.getReps()).isEqualTo(jsonObject.get("reps"));
    assertThat(updatedSet.getRir()).isEqualTo(jsonObject.get("rir"));
    assertThat(updatedSet.getWeight()).isEqualTo(jsonObject.get("weight"));
    assertThat(updatedSet.getCompletedAt()).isNull();
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotUpdateSetWhenRequestIsNotValidRequestAndReturnBadRequest() throws Exception {

    final JSONObject jsonObject = getJsonObject();
    jsonObject.put("rir", -1.0);

    mockMvc
        .perform(patch(PATH, SET_ID).contentType(APPLICATION_JSON).content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Invalid request content."))
        .andExpect(jsonPath("$.validationErrors").isArray())
        .andExpect(jsonPath("$.validationErrors[0].field").value("rir"))
        .andExpect(jsonPath("$.validationErrors[0].message").isString());
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldNotUpdateSetDataWhenUserHasNoAccessAndReturnForbidden() throws Exception {

    mockMvc
        .perform(
            patch(PATH, SET_ID).contentType(APPLICATION_JSON).content(getJsonObject().toString()))
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(
            jsonPath("$.message").value("You do not have permissions to access the resource"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotUpdateSetDataWhenSetIsNotFoundAndReturnNotFound() throws Exception {

    mockMvc
        .perform(
            patch(PATH, UUID.randomUUID())
                .contentType(APPLICATION_JSON)
                .content(getJsonObject().toString()))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Entity not found"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());
  }
}
