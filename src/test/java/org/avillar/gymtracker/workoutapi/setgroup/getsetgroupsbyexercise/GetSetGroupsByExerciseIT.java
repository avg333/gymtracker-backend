package org.avillar.gymtracker.workoutapi.setgroup.getsetgroupsbyexercise;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.SetGroupDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
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
class GetSetGroupsByExerciseIT {

  private static final String PATH = "/api/v1/users/{userId}/exercises/{exerciseId}/setGroups";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String USER_OK_ID = "e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65";
  private static final String EXERCISE_ID = "b744c8c9-ef76-4aa1-b3e9-4b2b1be50540";

  @Autowired private MockMvc mockMvc;
  @Autowired private SetGroupDao setGroupDao;

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldGetExerciseSetGroupsSuccessfully() throws Exception {

    final List<SetGroupEntity> setGroupEntities =
        setGroupDao.getSetGroupsFullByUserIdAndExerciseId(
            UUID.fromString(USER_OK_ID), UUID.fromString(EXERCISE_ID));

    mockMvc
        .perform(get(PATH, USER_OK_ID, EXERCISE_ID))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*").isArray())
        .andExpect(jsonPath("$.*", hasSize(setGroupEntities.size())));

    // TODO check that the response is the same as the one in the database
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldReturnOkWithEmptyListWhenHasNoResults() throws Exception {

    mockMvc
        .perform(get(PATH, USER_OK_ID, UUID.randomUUID()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*").isEmpty());
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldReturnForbiddenWhenUserHasNoReadAccessToUserSetGroups() throws Exception {

    mockMvc
        .perform(get(PATH, USER_OK_ID, UUID.randomUUID()))
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(
            jsonPath("$.message").value("You do not have permissions to access the resource"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());
  }
}
