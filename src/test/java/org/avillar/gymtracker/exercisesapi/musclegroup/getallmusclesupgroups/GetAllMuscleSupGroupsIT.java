package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclesupgroups;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleSupGroupDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleSupGroupEntity;
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
    scripts = "classpath:exerciseapi/data.sql",
    executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class GetAllMuscleSupGroupsIT {

  private static final String PATH = "/api/v1/muscleSupGroups";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";

  @Autowired private MockMvc mockMvc;
  @Autowired private MuscleSupGroupDao muscleSupGroupDao;

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldGetAllMuscleSupGroupsSuccessfully() throws Exception {

    final List<MuscleSupGroupEntity> muscleSupGroupEntities = muscleSupGroupDao.getAll();

    final int muscleSupGroupsSize = muscleSupGroupEntities.size();
    final int muscleGroupsSize = muscleSupGroupEntities.get(0).getMuscleGroups().size();
    final int muscleSubGroupsSize =
        muscleSupGroupEntities.get(0).getMuscleGroups().stream()
            .findFirst()
            .get()
            .getMuscleSubGroups()
            .size();

    for (int i = 0; i < 1; i++) {
      mockMvc
          .perform(get(PATH))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.*", hasSize(muscleSupGroupsSize)))
          .andExpect(jsonPath("$.[0].muscleGroups.*", hasSize(muscleGroupsSize)));
    }
  }
}
