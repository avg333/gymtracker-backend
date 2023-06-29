package org.avillar.gymtracker.workoutapi.workout.createworkout;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.authapi.auth.domain.UserApp;
import org.avillar.gymtracker.authapi.auth.domain.UserDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class CreateWorkoutTest {

  final List<Workout> workouts = new ArrayList<>();
  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private UserDao userDao;

  @AfterEach
  void afterEach() {
    var works = workoutDao.findAll();
    if (!works.isEmpty()) workoutDao.deleteById(works.get(0).getId());
    workouts.clear();
  }

  @Test
  @WithUserDetails("adrian")
  void deleteOneWorkout() throws Exception {
    final UserApp userApp = userDao.findByUsername("adrian");
    final String updateWorkoutDateRequest =
        """
{
"date": "%s",
"description": "Description TEST"
}
""";

    mockMvc
        .perform(
            post("/workout-api/users/" + userApp.getId() + "/workouts")
                .contentType(APPLICATION_JSON)
                .content(String.format(updateWorkoutDateRequest, "2023-06-28")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.date").value("2023-06-28"));

    // final Optional<Workout> workoutDb = workoutDao.findById(workoutId);
    // Assertions.assertTrue(workoutDb.isPresent());
    // Assertions.assertEquals("newDescription", workoutDb.get().getDescription());
  }

  @Test
  @WithUserDetails("adrian")
  void deleteOneWorkout2() throws Exception {
    final Workout workout = workouts.get(0);
    final String updateWorkoutDateRequest = """
{"description": null}
""";

    mockMvc
        .perform(
            patch("/workout-api/workouts/" + workout.getId() + "/date")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest))
        .andDo(print())
        .andExpect(status().isBadRequest());
  }

  @Test
  @WithUserDetails("chema")
  void deleteNotFoundAndNotPermission() throws Exception {
    final Workout workout = workouts.get(0);
    final String updateWorkoutDateRequest = """
{"description": "newDescription"}
""";
    mockMvc
        .perform(
            patch("/workout-api/workouts/" + UUID.randomUUID() + "/description")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest))
        .andDo(print())
        .andExpect(status().isNotFound());
    mockMvc
        .perform(
            patch("/workout-api/workouts/" + workout.getId() + "/description")
                .contentType(APPLICATION_JSON)
                .content(updateWorkoutDateRequest))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
}
