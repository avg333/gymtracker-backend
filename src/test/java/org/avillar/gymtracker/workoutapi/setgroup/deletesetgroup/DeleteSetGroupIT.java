package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.SetDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.setgroup.SetGroupDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.workout.WorkoutDao;
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
class DeleteSetGroupIT {

  private static final String PATH = "/api/v1/setGroups/{setId}";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private final String[] SET_GROUPS_ID = {
    "7a8f6de6-7c6d-466e-bacb-9dcb26bd78ad",
    "431d00a4-485b-47b4-bad3-2033bcfe6170",
    "5a9503e7-cdb9-4824-b5d5-174c9a5835ce",
    "6e3acfc2-d8a4-4dd4-b561-3ac4439feecd",
    "1e59a819-8e46-4a1b-8a32-3fa2918b98d7"
  };

  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private SetGroupDao setGroupDao;
  @Autowired private SetDao setDao;

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldDeleteSetSuccessfullyAndReturnNoContent() throws Exception {

    final int deletedSetGroupIdx = 2;

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    final int setGroupSetsSize =
        setGroupDao
            .getSetGroupFullByIds(Set.of(UUID.fromString(SET_GROUPS_ID[deletedSetGroupIdx])))
            .get(0)
            .getSets()
            .size();

    mockMvc
        .perform(delete(PATH, SET_GROUPS_ID[deletedSetGroupIdx]))
        .andDo(print())
        .andExpect(status().isNoContent());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize - 1);
    assertThat(setDao.findAll()).hasSize(setsSize - setGroupSetsSize);

    for (int i = 0; i < SET_GROUPS_ID.length; i++) {
      if (i < deletedSetGroupIdx) {
        assertThat(setGroupDao.findById(UUID.fromString(SET_GROUPS_ID[i]))).isPresent();
      } else if (i > deletedSetGroupIdx) {
        assertThat(setGroupDao.findById(UUID.fromString(SET_GROUPS_ID[i]))).isPresent();
        assertThat(setGroupDao.findById(UUID.fromString(SET_GROUPS_ID[i])).get().getListOrder())
            .isEqualTo(i - 1);
      } else {
        assertThat(setGroupDao.findById(UUID.fromString(SET_GROUPS_ID[i]))).isNotPresent();
      }
    }
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldNotDeleteSetGroupWhenUserHasNoAccessAndReturnForbidden() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    mockMvc
        .perform(delete(PATH, SET_GROUPS_ID[1]))
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
  void shouldNotDeleteSetGroupWhenSetGroupIsNotFoundAndReturnNotFound() throws Exception {

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
