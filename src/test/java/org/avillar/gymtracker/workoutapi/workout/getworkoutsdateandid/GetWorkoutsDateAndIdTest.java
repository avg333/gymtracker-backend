package org.avillar.gymtracker.workoutapi.workout.getworkoutsdateandid;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.WorkoutDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutDateAndId;
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
class GetWorkoutsDateAndIdTest {

  private static final String PATH = "/api/v1/users/{userId}/workouts/dates";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String USER_ID = "e0e5af19-7c1e-4d1c-96a8-fedc1e1ccc65";

  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldReturnWorkout() throws Exception {

    final List<WorkoutDateAndId> workoutsDateAndId =
        workoutDao.getWorkoutsIdAndDatesByUser(UUID.fromString(USER_ID));
    final WorkoutDateAndId first = workoutsDateAndId.get(0);
    final WorkoutDateAndId last = workoutsDateAndId.get(workoutsDateAndId.size() - 1);

    mockMvc
        .perform(get(PATH, USER_ID))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.workoutsDateAndId").exists())
        .andExpect(jsonPath("$.workoutsDateAndId").isMap())
        .andExpect(
            jsonPath("$.workoutsDateAndId['" + first.getDate().toString() + "']")
                .value(first.getId().toString()))
        .andExpect(
            jsonPath("$.workoutsDateAndId['" + last.getDate().toString() + "']")
                .value(last.getId().toString()));
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldReturnForbiddenWhenUserHasNoAccess() throws Exception {

    mockMvc
        .perform(get(PATH, USER_ID))
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(
            jsonPath("$.message").value("You do not have permissions to access the resource"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());
  }
}
