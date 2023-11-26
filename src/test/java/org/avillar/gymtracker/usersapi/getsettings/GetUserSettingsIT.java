package org.avillar.gymtracker.usersapi.getsettings;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.avillar.gymtracker.usersapi.common.adapter.repository.SettingsDao;
import org.avillar.gymtracker.usersapi.common.adapter.repository.model.SettingsEntity;
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
class GetUserSettingsIT {

  private static final String PATH = "/api/v1/users/{userId}/settings";

  private static final String USER_ID_WITH_SETTINGS_OK = "e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65";
  private static final String USER_ID_WITHOUT_SETTINGS_OK = "a672f95d-a2a9-46af-b82d-3d542ce27fc3";
  private static final String USER_NAME_WITH_SETTINGS_OK = "IT_TEST_USER_WITH_SETTINGS_OK";
  private static final String USER_NAME_WITHOUT_SETTINGS_OK = "IT_TEST_USER_WITHOUT_SETTINGS_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";

  @Autowired private MockMvc mockMvc;

  @Autowired private SettingsDao settingsDao;

  @Test
  @WithUserDetails(USER_NAME_WITH_SETTINGS_OK)
  void shouldGetUserSettingsByUserIdSuccessfully() throws Exception {

    final SettingsEntity settingsEntity =
        settingsDao.findByUserId(UUID.fromString(USER_ID_WITH_SETTINGS_OK)).get();

    mockMvc
        .perform(get(PATH, USER_ID_WITH_SETTINGS_OK))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.internationalSystem").value(settingsEntity.getInternationalSystem()))
        .andExpect(jsonPath("$.selectedIncrement").value(settingsEntity.getSelectedIncrement()))
        .andExpect(jsonPath("$.selectedBar").value(settingsEntity.getSelectedBar()))
        .andExpect(jsonPath("$.selectedPlates").isArray())
        .andExpect(
            jsonPath("$.selectedPlates", hasSize(settingsEntity.getSelectedPlates().size())));
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldReturnForbiddenWhenUserHasNoAccess() throws Exception {

    mockMvc
        .perform(get(PATH, USER_ID_WITH_SETTINGS_OK))
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(
            jsonPath("$.message").value("You do not have permissions to access the resource"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());
  }

  @Test
  @WithUserDetails(USER_NAME_WITHOUT_SETTINGS_OK)
  void shouldReturnNotFoundWhenUserHasNoSettings() throws Exception {

    mockMvc
        .perform(get(PATH, USER_ID_WITHOUT_SETTINGS_OK))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Entity not found"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());
  }

  @Test
  @WithUserDetails(USER_NAME_WITHOUT_SETTINGS_OK)
  void shouldReturnNotFoundWhenUserIdNotExists() throws Exception {

    mockMvc
        .perform(get(PATH, UUID.randomUUID()))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Entity not found"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());
  }
}
