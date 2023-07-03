package org.avillar.gymtracker.exercisesapi.getallloadtypes;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashSet;
import java.util.List;
import org.avillar.gymtracker.exercisesapi.domain.LoadType;
import org.avillar.gymtracker.exercisesapi.domain.LoadTypeDao;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class GetAllLoadTypesTest {

  private static final String USER_NAME_OK = "adrian";
  private final EasyRandom easyRandom = new EasyRandom();
  @Autowired private MockMvc mockMvc;

  @Autowired private LoadTypeDao loadTypeDao;

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
        .perform(get("/exercises-api/loadTypes"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(10)));
  }
}
