package org.avillar.gymtracker.workoutapi.setgroup.getsetgroup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
class GetSetGroupIT {

  private static final String PATH = "/api/v1/setGroups/{setGroupId}";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String SET_GROUP_ID = "7a8f6de6-7c6d-466e-bacb-9dcb26bd78ad";

  @Autowired private MockMvc mockMvc;
  @Autowired private SetGroupDao setGroupDao;

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldReturnSetGroup() throws Exception {

    final SetGroupEntity setGroupEntity =
        setGroupDao.getSetGroupWithWorkoutById(UUID.fromString(SET_GROUP_ID)).get();

    mockMvc
        .perform(get(PATH, SET_GROUP_ID))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(setGroupEntity.getId().toString()))
        .andExpect(jsonPath("$.listOrder").value(setGroupEntity.getListOrder()))
        .andExpect(jsonPath("$.description").value(setGroupEntity.getDescription()))
        .andExpect(jsonPath("$.exerciseId").value(setGroupEntity.getExerciseId().toString()))
        .andExpect(jsonPath("$.workout.id").value(setGroupEntity.getWorkout().getId().toString()))
        .andExpect(jsonPath("$.sets").doesNotExist());
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldReturnForbiddenWhenUserHasNoReadAccessToSetGroup() throws Exception {

    mockMvc
        .perform(get(PATH, SET_GROUP_ID))
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
  void shouldReturnNotFoundWhenSetGroupNotExists() throws Exception {

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
