// package org.avillar.gymtracker.exercisesapi.exercise.deleteexercise;
//
// import static org.assertj.core.api.Assertions.assertThat;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// import java.util.UUID;
// import org.avillar.gymtracker.exercisesapi.exercise.ExerciseIntegrationBaseTest;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.springframework.security.test.context.support.WithUserDetails;
//
// class DeleteExerciseTest extends ExerciseIntegrationBaseTest {
//
//  private static final String ENDPOINT = "/api/exercises/{exerciseId}";
//
//  @BeforeAll
//  public void beforeAll() {
//    this.createAll();
//  }
//
//  @AfterAll
//  public void afterAll() {
//    this.deleteAll();
//  }
//
//  @Test
//  @WithUserDetails(USER_NAME_OK)
//  void deleteExerciseOk() throws Exception {
//    final UUID exerciseId = getPrivateExerciseOfUser().getId();
//
//    mockMvc.perform(delete(ENDPOINT,
// exerciseId)).andDo(print()).andExpect(status().isNoContent());
//
//    assertThat(exerciseDao.findById(exerciseId)).isNotPresent();
//    assertThat(
//            muscleGroupExerciseDao.findAll().stream()
//                .filter(mge -> mge.getExercise().getId().equals(exerciseId))
//                .findAny())
//        .isNotPresent();
//  }
//
//  @Test
//  @WithUserDetails(USER_NAME_OK)
//  void deleteExerciseKoWithUsages() throws Exception {
//    // TODO Finish this test
//  }
//
//  @Test
//  @WithUserDetails(USER_NAME_OK)
//  void deletePublicExerciseKoNotAccess() throws Exception {
//    mockMvc
//        .perform(delete(ENDPOINT, getPublicExercise().getId()))
//        .andDo(print())
//        .andExpect(status().isForbidden());
//  }
//
//  @Test
//  @WithUserDetails(USER_NAME_OK)
//  void deletePrivateExerciseKoNotAccess() throws Exception {
//    mockMvc
//        .perform(delete(ENDPOINT, getPrivateExerciseOfOtherUser().getId()))
//        .andDo(print())
//        .andExpect(status().isForbidden());
//  }
//
//  @Test
//  @WithUserDetails(USER_NAME_OK)
//  void deleteExerciseKoNotFound() throws Exception {
//    mockMvc
//        .perform(delete(ENDPOINT, UUID.randomUUID()))
//        .andDo(print())
//        .andExpect(status().isNotFound());
//  }
// }
