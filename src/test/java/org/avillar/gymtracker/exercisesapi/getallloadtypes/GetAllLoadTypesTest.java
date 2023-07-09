package org.avillar.gymtracker.exercisesapi.getallloadtypes;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;
import org.avillar.gymtracker.IntegrationBaseTest;
import org.avillar.gymtracker.exercisesapi.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.domain.LoadTypeDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

class GetAllLoadTypesTest extends IntegrationBaseTest {

  private static final String ENDPOINT = "/exercises-api/loadTypes";

  @Autowired private MockMvc mockMvc;
  @Autowired private LoadTypeDao loadTypeDao;

  @BeforeAll
  public void beforeAll() {
    this.startRedis();
    this.createUsers();
  }

  @AfterAll
  public void afterAll() {
    this.stopRedis();
    this.deleteUsers();
  }

  @BeforeEach
  void beforeEach() {
    loadTypeDao.deleteAll();
    final List<LoadType> loadTypes = easyRandom.objects(LoadType.class, 10).toList();
    loadTypes.forEach(
        loadType -> {
          loadType.setId(null);
          loadType.setExercises(new HashSet<>());
        });
    loadTypeDao.saveAll(loadTypes);
  }

  @AfterEach
  void afterEach() {
    loadTypeDao.deleteAll();
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void getAllLoadTypes() throws Exception {
    mockMvc
        .perform(get(ENDPOINT))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(10)));

    mockMvc
        .perform(get(ENDPOINT))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(10)));
  }
}
