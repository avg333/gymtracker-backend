package org.avillar.gymtracker.workoutapi.set.getset;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.SetDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
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
class GetSetIT {

  private static final String PATH = "/api/v1/sets/{setId}";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String SET_ID = "c282a196-69f4-4de2-94fa-a60ec5201686";

  @Autowired private MockMvc mockMvc;
  @Autowired private SetDao setDao;

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldReturnSet() throws Exception {

    final SetEntity setEntity = setDao.getSetFullById(UUID.fromString(SET_ID)).get();

    mockMvc
        .perform(get(PATH, SET_ID))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(setEntity.getId().toString()))
        .andExpect(jsonPath("$.listOrder").value(setEntity.getListOrder()))
        .andExpect(jsonPath("$.description").value(setEntity.getDescription()))
        .andExpect(jsonPath("$.reps").value(setEntity.getReps()))
        .andExpect(jsonPath("$.rir").value(setEntity.getRir()))
        .andExpect(jsonPath("$.weight").value(setEntity.getWeight()))
        .andExpect(jsonPath("$.completedAt").isEmpty())
        .andExpect(jsonPath("$.setGroup.id").value(setEntity.getSetGroup().getId().toString()));
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldReturnForbiddenWhenUserHasNoAccessToSet() throws Exception {

    mockMvc
        .perform(get(PATH, SET_ID))
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
  void shouldReturnNotFoundWhenSetIsNotFound() throws Exception {

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
