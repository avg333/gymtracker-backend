package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.model.SetGroupEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.WorkoutDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.model.WorkoutEntity;
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
class GetWorkoutWithSetGroupsIT {

  private static final String PATH = "/api/v1/workouts/{workoutId}/sgs";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String WORKOUT_ID = "f48ecca5-a840-4c2f-8788-f82dd9103239";

  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldReturnWorkoutSuccessfully() throws Exception {

    final WorkoutEntity workoutEntity =
        workoutDao.getWorkoutWithSetGroupsById(UUID.fromString(WORKOUT_ID)).get();
    final List<SetGroupEntity> setGroupEntities = new ArrayList<>(workoutEntity.getSetGroups());
    final int last = setGroupEntities.size() - 1;

    mockMvc
        .perform(get(PATH, WORKOUT_ID))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(workoutEntity.getId().toString()))
        .andExpect(jsonPath("$.date").value(workoutEntity.getDate().toString()))
        .andExpect(jsonPath("$.description").value(workoutEntity.getDescription()))
        .andExpect(jsonPath("$.userId").value(workoutEntity.getUserId().toString()))
        .andExpect(jsonPath("$.setGroups").isArray())
        .andExpect(jsonPath("$.setGroups[0].id").value(setGroupEntities.get(0).getId().toString()))
        .andExpect(
            jsonPath("$.setGroups[0].listOrder").value(setGroupEntities.get(0).getListOrder()))
        .andExpect(
            jsonPath("$.setGroups[0].description").value(setGroupEntities.get(0).getDescription()))
        .andExpect(
            jsonPath("$.setGroups[0].exerciseId")
                .value(setGroupEntities.get(0).getExerciseId().toString()))
        .andExpect(jsonPath("$.setGroups[0].sets").doesNotExist())
        .andExpect(
            jsonPath("$.setGroups[" + last + "].id")
                .value(setGroupEntities.get(last).getId().toString()))
        .andExpect(
            jsonPath("$.setGroups[" + last + "].listOrder")
                .value(setGroupEntities.get(last).getListOrder()))
        .andExpect(
            jsonPath("$.setGroups[" + last + "].description")
                .value(setGroupEntities.get(last).getDescription()))
        .andExpect(
            jsonPath("$.setGroups[" + last + "].exerciseId")
                .value(setGroupEntities.get(last).getExerciseId().toString()))
        .andExpect(jsonPath("$.setGroups[" + last + "].sets").doesNotExist());
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldReturnForbiddenWhenUserHasNoAccess() throws Exception {

    mockMvc
        .perform(get(PATH, WORKOUT_ID))
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
  void shouldReturnNotFoundWhenWorkoutNotExits() throws Exception {

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
