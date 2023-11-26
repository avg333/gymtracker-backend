package org.avillar.gymtracker.workoutapi.set.deleteset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.SetDao;
import org.avillar.gymtracker.workoutapi.common.adapter.repository.set.model.SetEntity;
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
class DeleteSetIT {

  private static final String PATH = "/api/v1/sets/{setId}";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String USER_NAME_KO = "IT_TEST_USER_KO";
  private static final String[] SETS_ID = {
    "b254efa7-7aea-45dd-bbd3-d46db28737bd",
    "967f6805-152b-4e92-a3ba-cd420c133b59",
    "c282a196-69f4-4de2-94fa-a60ec5201686",
    "e4e404b8-36ab-452a-9274-ea03983ed66d"
  };

  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private SetGroupDao setGroupDao;
  @Autowired private SetDao setDao;

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldDeleteSetSuccessfullyAndReturnNoContent() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    final int setIndex = 1;

    mockMvc
        .perform(delete(PATH, SETS_ID[setIndex]))
        .andDo(print())
        .andExpect(status().isNoContent());

    assertThat(workoutDao.findAll()).hasSize(workoutsSize);
    assertThat(setGroupDao.findAll()).hasSize(setGroupsSize);
    assertThat(setDao.findAll()).hasSize(setsSize - 1);

    for (int i = 0; i < SETS_ID.length; i++) {
      final Optional<SetEntity> setEntity = setDao.findById(UUID.fromString(SETS_ID[i]));
      if (i < setIndex) {
        assertThat(setEntity).isPresent();
        assertThat(setEntity.get().getListOrder()).isEqualTo(i);
      } else if (i > setIndex) {
        assertThat(setEntity).isPresent();
        assertThat(setEntity.get().getListOrder()).isEqualTo(i - 1);
      } else {
        assertThat(setEntity).isNotPresent();
      }
    }
  }

  @Test
  @WithUserDetails(USER_NAME_KO)
  void shouldNotDeleteSetWhenUserHasNoAccessAndReturnForbidden() throws Exception {

    final int workoutsSize = workoutDao.findAll().size();
    final int setGroupsSize = setGroupDao.findAll().size();
    final int setsSize = setDao.findAll().size();

    mockMvc
        .perform(delete(PATH, SETS_ID[2]))
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
  void shouldNotDeleteSetWhenSetIsNotFoundAndReturnNotFound() throws Exception {

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
