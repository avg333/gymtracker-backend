package org.avillar.gymtracker.exercisesapi.getallmusclesupgroups;

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

class GetAllMuscleSupGroupsTest extends IntegrationBaseTest {

  private static final String ENDPOINT = "/exercises-api/muscleSupGroups";

  private static final int TOTAL_M_SUP_G = 3;
  private static final int TOTAL_M_G = 4;
  private static final int TOTAL_M_SUB_G = 5;

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
        easyRandom.objects(MuscleSupGroup.class, TOTAL_M_SUP_G).toList();
    final List<MuscleGroup> muscleGroups = new ArrayList<>();
    final List<MuscleSubGroup> muscleSubGroups = new ArrayList<>();
    muscleSupGroups.forEach(
        mSupG -> {
          mSupG.setId(null);
          mSupG.setMuscleGroups(new HashSet<>());
          final List<MuscleGroup> muscleGroupsAux =
              easyRandom.objects(MuscleGroup.class, TOTAL_M_G).toList();
          muscleGroupsAux.forEach(
              mg -> {
                mg.setId(null);
                mg.setMuscleSubGroups(new HashSet<>());
                mg.setMuscleSupGroups(Set.of(mSupG));
                final List<MuscleSubGroup> muscleSubGroupsAux =
                    easyRandom.objects(MuscleSubGroup.class, TOTAL_M_SUB_G).toList();
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
  void getAllMuscleSupGroupsWithMuscleGroupsWithMuscleSubGroups() throws Exception {
    mockMvc
        .perform(get(ENDPOINT))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(TOTAL_M_SUP_G)))
        .andExpect(jsonPath("$.[0].muscleGroups.*", hasSize(TOTAL_M_G)))
        .andExpect(jsonPath("$.[0].muscleGroups.[0].muscleSubGroups.*", hasSize(TOTAL_M_SUB_G)));

    mockMvc
        .perform(get(ENDPOINT))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.*", hasSize(TOTAL_M_SUP_G)))
        .andExpect(jsonPath("$.[0].muscleGroups.*", hasSize(TOTAL_M_G)))
        .andExpect(jsonPath("$.[0].muscleGroups.[0].muscleSubGroups.*", hasSize(TOTAL_M_SUB_G)));
  }
}
