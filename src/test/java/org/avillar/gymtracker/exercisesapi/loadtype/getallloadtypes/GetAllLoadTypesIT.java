package org.avillar.gymtracker.exercisesapi.loadtype.getallloadtypes;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.LoadTypeDao;
import org.avillar.gymtracker.exercisesapi.common.adapter.repository.model.LoadTypeEntity;
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
class GetAllLoadTypesIT {

  private static final String PATH = "/api/v1/loadTypes";

  private static final String USER_NAME_OK = "IT_TEST_USER_OK";

  @Autowired private MockMvc mockMvc;
  @Autowired private LoadTypeDao loadTypeDao;

  @Test
  @WithUserDetails(USER_NAME_OK)
  void shouldReturnAllLoadTypesSuccessfully() throws Exception {

    final List<LoadTypeEntity> entities = loadTypeDao.findAll();
    final int last = entities.size() - 1;

    for (int i = 0; i < 1; i++) {
      mockMvc
          .perform(get(PATH))
          .andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.*").isArray())
          .andExpect(jsonPath("$.*", hasSize(entities.size())))
          .andExpect(jsonPath("$.[0].id").value(entities.get(0).getId().toString()))
          .andExpect(jsonPath("$.[0].name").value(entities.get(0).getName()))
          .andExpect(jsonPath("$.[0].description").value(entities.get(0).getDescription()))
          .andExpect(jsonPath("$.[" + last + "].id").value(entities.get(last).getId().toString()))
          .andExpect(jsonPath("$.[" + last + "].name").value(entities.get(last).getName()))
          .andExpect(
              jsonPath("$.[" + last + "].description").value(entities.get(last).getDescription()));
    }
  }
}
