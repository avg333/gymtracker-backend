package org.avillar.gymtracker.workoutapi.setgroup.updatesetgroupdescription;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.SetGroupDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
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
class UpdateSetGroupDescriptionIT {

  private static final String PATH = "/api/v1/setGroups/{setId}/description";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String SET_GROUP_ID = "7a8f6de6-7c6d-466e-bacb-9dcb26bd78ad";

  @Autowired private MockMvc mockMvc;
  @Autowired private SetGroupDao setGroupDao;

  private static JSONObject getJsonObject() throws JSONException {
    final JSONObject updateSetDataRequestDto = new JSONObject();
    updateSetDataRequestDto.put("description", "Description TEST");
    return updateSetDataRequestDto;
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldUpdateSetGroupDescriptionSuccessfullyAndReturnSetGroupDescription() throws Exception {
    final JSONObject jsonObject = getJsonObject();

    mockMvc
        .perform(
            patch(PATH, SET_GROUP_ID).contentType(APPLICATION_JSON).content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.description").value(jsonObject.get("description")));

    final Optional<SetGroupEntity> optionalSetEntity =
        setGroupDao.findById(UUID.fromString(SET_GROUP_ID));
    assertThat(optionalSetEntity).isPresent();
    assertThat(optionalSetEntity.get().getDescription()).isEqualTo(jsonObject.get("description"));
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotUpdateSetGroupDescriptionWhenRequestIsNotValidAndReturnBadRequest()
      throws Exception {

    final JSONObject jsonObject = getJsonObject();
    jsonObject.put("description", "a".repeat(256));

    mockMvc
        .perform(
            patch(PATH, SET_GROUP_ID).contentType(APPLICATION_JSON).content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Invalid request content."))
        .andExpect(jsonPath("$.validationErrors").isArray())
        .andExpect(jsonPath("$.validationErrors[0].field").value("description"))
        .andExpect(jsonPath("$.validationErrors[0].message").isString());
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldNotUpdateSetGroupDescriptionWhenUserHasNoAccessAndReturnForbidden() throws Exception {

    mockMvc
        .perform(
            patch(PATH, SET_GROUP_ID)
                .contentType(APPLICATION_JSON)
                .content(getJsonObject().toString()))
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
  void shouldNotUpdateSetGroupDescriptionWhenSetGroupIsNotFoundAndReturnNotFound()
      throws Exception {

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
