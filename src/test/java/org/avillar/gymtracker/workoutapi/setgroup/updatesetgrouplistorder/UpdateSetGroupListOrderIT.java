package org.avillar.gymtracker.workoutapi.setgroup.updatesetgrouplistorder;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("it")
@Execution(ExecutionMode.SAME_THREAD)
@Sql(
    scripts = "classpath:workoutapi/data.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class UpdateSetGroupListOrderIT {

  private static final String PATH = "/api/v1/setGroups/{setGroupId}/listOrder";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String[] SET_GROUPS_ID = {
    "7a8f6de6-7c6d-466e-bacb-9dcb26bd78ad",
    "431d00a4-485b-47b4-bad3-2033bcfe6170",
    "5a9503e7-cdb9-4824-b5d5-174c9a5835ce",
    "6e3acfc2-d8a4-4dd4-b561-3ac4439feecd",
    "1e59a819-8e46-4a1b-8a32-3fa2918b98d7"
  };

  @Autowired private MockMvc mockMvc;
  @Autowired private SetGroupDao setGroupDao;

  private static JSONObject getJsonObject() throws JSONException {
    final JSONObject updateSetDataRequestDto = new JSONObject();
    updateSetDataRequestDto.put("listOrder", 0);
    return updateSetDataRequestDto;
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldUpdateSetDataSuccessfullyAndUpdatedSetData() throws Exception {
    final JSONObject jsonObject = getJsonObject();

    final int updatedSetGroupIdx = 2;
    final int newPosition = (int) jsonObject.get("listOrder");

    mockMvc
        .perform(
            patch(PATH, SET_GROUPS_ID[updatedSetGroupIdx])
                .contentType(APPLICATION_JSON)
                .content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isOk());

    final Optional<SetGroupEntity> firstSetGroup =
        setGroupDao.findById(UUID.fromString(SET_GROUPS_ID[updatedSetGroupIdx]));
    assertThat(firstSetGroup).isPresent();
    assertThat(firstSetGroup.get().getListOrder()).isEqualTo(newPosition);
    final Optional<SetGroupEntity> secondSetGroup =
        setGroupDao.findById(UUID.fromString(SET_GROUPS_ID[0]));
    assertThat(secondSetGroup).isPresent();
    assertThat(secondSetGroup.get().getListOrder()).isEqualTo(1);
    final Optional<SetGroupEntity> thirdSetGroup =
        setGroupDao.findById(UUID.fromString(SET_GROUPS_ID[1]));
    assertThat(thirdSetGroup).isPresent();
    assertThat(thirdSetGroup.get().getListOrder()).isEqualTo(2);
    final Optional<SetGroupEntity> fourthSetGroup =
        setGroupDao.findById(UUID.fromString(SET_GROUPS_ID[3]));
    assertThat(fourthSetGroup).isPresent();
    assertThat(fourthSetGroup.get().getListOrder()).isEqualTo(3);
    final Optional<SetGroupEntity> fifthSetGroup =
        setGroupDao.findById(UUID.fromString(SET_GROUPS_ID[4]));
    assertThat(fifthSetGroup).isPresent();
    assertThat(fifthSetGroup.get().getListOrder()).isEqualTo(4);
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotUpdateSetGroupListOrderWhenRequestIsNotValidAndReturnBadRequest() throws Exception {

    final JSONObject jsonObject = getJsonObject();
    jsonObject.put("listOrder", -1);

    mockMvc
        .perform(
            patch(PATH, SET_GROUPS_ID[1])
                .contentType(APPLICATION_JSON)
                .content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Invalid request content."))
        .andExpect(jsonPath("$.validationErrors").isArray())
        .andExpect(jsonPath("$.validationErrors[0].field").value("listOrder"))
        .andExpect(jsonPath("$.validationErrors[0].message").isString());
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldNotUpdateSetGroupListOrderWhenUserHasNoAccessAndReturnForbidden() throws Exception {

    mockMvc
        .perform(
            patch(PATH, SET_GROUPS_ID[2])
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
  void shouldNotUpdateSetGroupListOrderWhenSetIsNotFoundAndReturnNotFound() throws Exception {

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
