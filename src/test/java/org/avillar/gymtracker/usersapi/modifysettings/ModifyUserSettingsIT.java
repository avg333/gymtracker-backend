package org.avillar.gymtracker.usersapi.modifysettings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.usersapi.common.adapter.repository.SettingsDao;
import org.avillar.gymtracker.usersapi.common.adapter.repository.model.SettingsEntity;
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
@Sql(scripts = "classpath:userapi/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class ModifyUserSettingsIT {

  private static final String PATH = "/api/v1/users/{userId}/settings";

  private static final String USER_ID_WITHOUT_SETTINGS_OK = "a672f95d-a2a9-46af-b82d-3d542ce27fc3";
  private static final String USER_NAME_WITHOUT_SETTINGS_OK = "IT_TEST_USER_WITHOUT_SETTINGS_OK";
  private static final String USER_ID_WITH_SETTINGS_OK = "e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65";
  private static final String USER_NAME_WITH_SETTINGS_OK = "IT_TEST_USER_WITH_SETTINGS_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";

  @Autowired private MockMvc mockMvc;
  @Autowired private SettingsDao settingsDao;

  private static JSONObject getJsonObject() throws JSONException {
    final JSONObject createSetRequestDto = new JSONObject();
    createSetRequestDto.put("internationalSystem", true);
    createSetRequestDto.put("selectedIncrement", 0.5);
    createSetRequestDto.put("selectedBar", 10);
    createSetRequestDto.put("selectedPlates", "[1.0, 2.5]");
    return createSetRequestDto;
  }

  private static String getRequest() {
    return "{\"internationalSystem\":true,\"selectedPlates\":[1.0,2.7],\"selectedBar\":10,\"selectedIncrement\":0.5}";
  }

  @Test
  @WithUserDetails(USER_NAME_WITHOUT_SETTINGS_OK)
  void shouldCreateExistentSettingsSuccessfully() throws Exception {

    final int settingsSize = settingsDao.findAll().size();

    mockMvc
        .perform(
            post(PATH, USER_ID_WITHOUT_SETTINGS_OK)
                .contentType(APPLICATION_JSON)
                .content(getRequest()))
        .andDo(print())
        .andExpect(status().isOk());

    assertThat(settingsDao.findAll()).hasSize(settingsSize + 1);

    validateSettingsEntity(USER_ID_WITHOUT_SETTINGS_OK, getJsonObject());
  }

  @Test
  @WithUserDetails(USER_NAME_WITH_SETTINGS_OK)
  void shouldUpdateExistentSettingsSuccessfully() throws Exception {

    final int settingsSize = settingsDao.findAll().size();

    mockMvc
        .perform(
            post(PATH, USER_ID_WITH_SETTINGS_OK)
                .contentType(APPLICATION_JSON)
                .content(getRequest()))
        .andDo(print())
        .andExpect(status().isOk());

    assertThat(settingsDao.findAll()).hasSize(settingsSize);

    validateSettingsEntity(USER_ID_WITH_SETTINGS_OK, getJsonObject());
  }

  @Test
  @WithUserDetails(USER_NAME_WITH_SETTINGS_OK)
  void shouldReturnBadRequestWhenRequestIsInvalid() throws Exception {

    final int settingsSize = settingsDao.findAll().size();

    final JSONObject jsonObject = getJsonObject();
    jsonObject.remove("selectedPlates");

    mockMvc
        .perform(
            post(PATH, USER_ID_WITH_SETTINGS_OK)
                .contentType(APPLICATION_JSON)
                .content(jsonObject.toString()))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Invalid request content."))
        .andExpect(jsonPath("$.validationErrors").isArray())
        .andExpect(jsonPath("$.validationErrors[0].field").value("selectedPlates"))
        .andExpect(jsonPath("$.validationErrors[0].message").isString());

    assertThat(settingsDao.findAll().size()).isEqualTo(settingsSize);
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldReturnForbiddenWhenUserHasNoAccess() throws Exception {

    final int settingsSize = settingsDao.findAll().size();

    mockMvc
        .perform(
            post(PATH, USER_ID_WITH_SETTINGS_OK)
                .contentType(APPLICATION_JSON)
                .content(getRequest()))
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(
            jsonPath("$.message").value("You do not have permissions to access the resource"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());

    assertThat(settingsDao.findAll().size()).isEqualTo(settingsSize);
  }

  private void validateSettingsEntity(final String userId, final JSONObject jsonObject)
      throws JSONException {
    final Optional<SettingsEntity> settingsEntity =
        settingsDao.findByUserId(UUID.fromString(userId));
    assertThat(settingsEntity).isPresent();
    final SettingsEntity settings = settingsEntity.get();
    assertThat(settings.getInternationalSystem())
        .isEqualTo(jsonObject.getBoolean("internationalSystem"));
    assertThat(settings.getSelectedIncrement())
        .isEqualTo(jsonObject.getDouble("selectedIncrement"));
    assertThat(settings.getSelectedBar()).isEqualTo(jsonObject.getDouble("selectedBar"));
  }
}
