package org.avillar.gymtracker.exercisesapi.getallmusclesubgroupsbymusclegroup;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.avillar.gymtracker.IntegrationBaseTest;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleGroupDao;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSubGroupDao;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSupGroup;
import org.avillar.gymtracker.exercisesapi.domain.MuscleSupGroupDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

class GetAllMuscleSubGroupsByMuscleGroupTest extends IntegrationBaseTest {

  private static final String ENDPOINT =
      "/exercises-api/muscleGroups/{muscleGroupId}/muscleSubGroups";

  @Autowired private MockMvc mockMvc;
  @Autowired private MuscleSupGroupDao muscleSupGroupDao;
  @Autowired private MuscleGroupDao muscleGroupDao;
  @Autowired private MuscleSubGroupDao muscleSubGroupDao;

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
    muscleSubGroupDao.deleteAll();
    muscleGroupDao.deleteAll();
    muscleSupGroupDao.deleteAll();

    final List<MuscleSupGroup> muscleSupGroups =
        easyRandom.objects(MuscleSupGroup.class, 3).toList();
    final List<MuscleGroup> muscleGroups = new ArrayList<>();
    final List<MuscleSubGroup> muscleSubGroups = new ArrayList<>();
    muscleSupGroups.forEach(
        mSupG -> {
          mSupG.setId(null);
          mSupG.setMuscleGroups(new HashSet<>());
          final List<MuscleGroup> muscleGroupsAux =
              easyRandom.objects(MuscleGroup.class, 4).toList();
          muscleGroupsAux.forEach(
              mg -> {
                mg.setId(null);
                mg.setMuscleSubGroups(new HashSet<>());
                mg.setMuscleSupGroups(Set.of(mSupG));
                final List<MuscleSubGroup> muscleSubGroupsAux =
                    easyRandom.objects(MuscleSubGroup.class, 5).toList();
                muscleSubGroupsAux.forEach(
                    mSubG -> {
                      mSubG.setId(null);
                      mSubG.setMuscleGroup(mg);
                    });
                muscleSubGroups.addAll(muscleSubGroupsAux);
              });
          muscleGroups.addAll(muscleGroupsAux);
        });
    muscleSupGroupDao.saveAll(muscleSupGroups);
    muscleGroupDao.saveAll(muscleGroups);
    muscleSubGroupDao.saveAll(muscleSubGroups);
  }

  @AfterEach
  void afterEach() {
    muscleSubGroupDao.deleteAll();
    muscleGroupDao.deleteAll();
    muscleSupGroupDao.deleteAll();
  }

  @Test
  @WithUserDetails(USER_NAME_OK)
  void getAllMuscleSubGroupsByMuscleGroup() throws Exception {
    final MuscleGroup muscleGroup = muscleGroupDao.findAll().get(0);
    mockMvc
        .perform(get(ENDPOINT, muscleGroup.getId()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(5)));

    mockMvc
        .perform(get(ENDPOINT, muscleGroup.getId()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(5)));
  }
}
