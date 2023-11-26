package org.avillar.gymtracker.workoutapi.workout.deleteworkout;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.SetDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.SetGroupDao;
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
class DeleteWorkoutIT {

  private static final String PATH = "/api/v1/workouts/{workoutId}";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private final String WORKOUT_ID = "f48ecca5-a840-4c2f-8788-f82dd9103239";

  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private SetGroupDao setGroupDao;
  @Autowired private SetDao setDao;

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldDeleteWorkoutSuccessfullyAndReturnNoContent() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    final WorkoutEntity workoutEntity =
        workoutDao.getFullWorkoutsByIds(Set.of(UUID.fromString(WORKOUT_ID))).stream()
            .findAny()
            .get();
    final List<UUID> setGroupIds =
        workoutEntity.getSetGroups().stream().map(SetGroupEntity::getId).toList();
    final List<UUID> setIds =
        workoutEntity.getSetGroups().stream()
            .flatMap(setGroup -> setGroup.getSets().stream())
            .map(SetEntity::getId)
            .toList();

    mockMvc.perform(delete(PATH, WORKOUT_ID)).andDo(print()).andExpect(status().isNoContent());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize - 1);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize - setGroupIds.size());
    assertThat(setDao.findAll()).hasSize(setsSize - setIds.size());
    assertThat(workoutDao.findById(UUID.fromString(WORKOUT_ID))).isNotPresent();
    assertThat(setGroupDao.findAllById(setGroupIds)).isEmpty();
    assertThat(setDao.findAllById(setIds)).isEmpty();
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldNotDeleteWorkoutWhenUserHasNoAccessAndReturnForbidden() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    mockMvc
        .perform(delete(PATH, WORKOUT_ID))
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(
            jsonPath("$.message").value("You do not have permissions to access the resource"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize);
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldNotDeleteWorkoutWhenWorkoutIsNotFoundAndReturnNotFound() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    mockMvc
        .perform(delete(PATH, UUID.randomUUID()))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.timestamp").isString())
        .andExpect(jsonPath("$.timestamp").isNotEmpty())
        .andExpect(jsonPath("$.message").value("Entity not found"))
        .andExpect(jsonPath("$.validationErrors").isEmpty());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize);
  }
}
