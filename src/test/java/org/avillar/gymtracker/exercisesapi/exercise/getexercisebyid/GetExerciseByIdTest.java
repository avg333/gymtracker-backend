// package org.avillar.gymtracker.exercisesapi.exercise.getexercisebyid;
//
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
// import java.util.UUID;
// import lombok.extern.slf4j.Slf4j;
// import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
// import org.avillar.gymtracker.exercisesapi.exercise.ExerciseIntegrationBaseTest;
// import org.junit.jupiter.api.AfterAll;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.Test;
// import org.springframework.security.test.context.support.WithUserDetails;
// import org.springframework.test.web.servlet.ResultActions;
//
// @Slf4j
// class GetExerciseByIdTest extends ExerciseIntegrationBaseTest {
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
//  void getExercisePublicByIdOk() throws Exception {
//    final Exercise exercise = getPublicExercise();
//
//    final ResultActions actions = mockMvc.perform(get(ENDPOINT, exercise.getId())).andDo(print());
//
//    assertResultActions(actions, exercise);
//  }
//
//  @Test
//  @WithUserDetails(USER_NAME_OK)
//  void getExercisePrivateByIdOk() throws Exception {
//    final Exercise exercise = getPrivateExerciseOfUser();
//
//    final ResultActions actions = mockMvc.perform(get(ENDPOINT, exercise.getId())).andDo(print());
//
//    assertResultActions(actions, exercise);
//  }
//
//  @Test
//  @WithUserDetails(USER_NAME_OK)
//  void getExercisePrivateByIdKoNotAccess() throws Exception {
//    mockMvc
//        .perform(get(ENDPOINT, getPrivateExerciseOfOtherUser().getId()))
//        .andDo(print())
//        .andExpect(status().isForbidden());
//  }
//
//  @Test
//  @WithUserDetails(USER_NAME_OK)
//  void getExerciseByIdKoNotFound() throws Exception {
//    mockMvc
//        .perform(get(ENDPOINT, UUID.randomUUID()))
//        .andDo(print())
//        .andExpect(status().isNotFound());
//  }
// }
