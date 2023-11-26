package org.avillar.gymtracker.exercisesapi.musclegroup.getallmusclegroupsbymusclesupgroup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.MuscleGroupEntity;
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
class GetAllMuscleGroupsByMuscleSupGroupIT {

  private static final String PATH = "/api/v1/muscleSupGroups/{muscleSupGroupId}/muscleGroups";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";
  private static final String MUSCLE_SUP_GROUP_ID = "82db5332-b990-479f-8f45-e616221ad8c8";

  @Autowired private MockMvc mockMvc;
  @Autowired private MuscleGroupDao muscleGroupDao;

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldGetAllMuscleGroupsByMuscleSupGroupSuccessfully() throws Exception {

    final List<MuscleGroupEntity> entities =
        muscleGroupDao.getAllMuscleGroupsByMuscleSupGroupId(UUID.fromString(MUSCLE_SUP_GROUP_ID));
    final int last = entities.size() - 1;

    // TODO Check subEntities

    for (int i = 0; i < 1; i++) {
      mockMvc.perform(get(PATH, MUSCLE_SUP_GROUP_ID)).andDo(print()).andExpect(status().isOk());
      /*
         .andExpect(jsonPath("$.*").isArray())
         .andExpect(jsonPath("$.*", hasSize(entities.size())))
         .andExpect(jsonPath("$.[0].id").value(entities.get(0).getId().toString()))
         .andExpect(jsonPath("$.[0].name").value(entities.get(0).getName()))
         .andExpect(jsonPath("$.[0].description").value(entities.get(0).getDescription()))
         .andExpect(
             jsonPath(
                 "$.[0].muscleSubGroups.*", hasSize(entities.get(0).getMuscleSubGroups().size())))
         .andExpect(
             jsonPath(
                 "$.[0].muscleSubGroups.*", hasSize(entities.get(0).getMuscleSubGroups().size())))
         .andExpect(jsonPath("$.[" + last + "].id").value(entities.get(last).getId().toString()))
         .andExpect(jsonPath("$.[" + last + "].name").value(entities.get(last).getName()))
         .andExpect(
             jsonPath("$.[" + last + "].description").value(entities.get(last).getDescription()))
         .andExpect(
             jsonPath(
                 "$.[" + last + "].muscleSubGroups.*",
                 hasSize(entities.get(last).getMuscleSubGroups().size())))
         .andExpect(
             jsonPath(
                 "$.[" + last + "].muscleSubGroups.*",
                 hasSize(entities.get(last).getMuscleSubGroups().size())));

      */
    }
  }
}
