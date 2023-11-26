package org.avillar.gymtracker.workoutapi.set.updatesetlistorder;

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
class UpdateSetListOrderIT {

  private static final String PATH = "/api/v1/sets/{setId}/listOrder";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String[] SETS_ID = {
    "b254efa7-7aea-45dd-bbd3-d46db28737bd",
    "967f6805-152b-4e92-a3ba-cd420c133b59",
    "c282a196-69f4-4de2-94fa-a60ec5201686",
    "e4e404b8-36ab-452a-9274-ea03983ed66d"
  };

  @Autowired private MockMvc mockMvc;
  @Autowired private SetDao setDao;

  private static JSONObject getJsonObject() throws JSONException {
    final JSONObject updateSetDataRequestDto = new JSONObject();
    updateSetDataRequestDto.put("listOrder", 1);
    return updateSetDataRequestDto;
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldUpdateSetDataSuccessfullyAndUpdatedSetData() throws Exception {
    final JSONObject jsonObject = getJsonObject();

    final int setIndex = 2;

    mockMvc
        .perform(
            patch(PATH, SETS_ID[setIndex])
                .contentType(APPLICATION_JSON)
                .content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isOk());

    final Optional<SetEntity> firstSet = setDao.findById(UUID.fromString(SETS_ID[0]));
    assertThat(firstSet).isPresent();
    assertThat(firstSet.get().getListOrder()).isZero();
    final Optional<SetEntity> secondSet = setDao.findById(UUID.fromString(SETS_ID[setIndex]));
    assertThat(secondSet).isPresent();
    assertThat(secondSet.get().getListOrder()).isEqualTo(jsonObject.get("listOrder"));
    final Optional<SetEntity> thirdSet = setDao.findById(UUID.fromString(SETS_ID[1]));
    assertThat(thirdSet).isPresent();
    assertThat(thirdSet.get().getListOrder()).isEqualTo(2);
    final Optional<SetEntity> fourthSet = setDao.findById(UUID.fromString(SETS_ID[3]));
    assertThat(fourthSet).isPresent();
    assertThat(fourthSet.get().getListOrder()).isEqualTo(3);
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotUpdateSetListOrderWhenRequestIsNotValidAndReturnBadRequest() throws Exception {

    final JSONObject jsonObject = getJsonObject();
    jsonObject.put("listOrder", -1);

    mockMvc
        .perform(
            patch(PATH, SETS_ID[2]).contentType(APPLICATION_JSON).content(jsonObject.toString()))
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
  void shouldNotUpdateSetListOrderWhenUserHasNoAccessAndReturnForbidden() throws Exception {

    mockMvc
        .perform(
            patch(PATH, SETS_ID[2])
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
  void shouldNotUpdateSetListOrderWhenSetIsNotFoundAndReturnNotFound() throws Exception {

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
