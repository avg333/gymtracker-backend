package org.avillar.gymtracker.workoutapi.workout.updateworkoutdate;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserApp.ActivityLevelEnum;
import org.avillar.gymtracker.authapi.domain.UserApp.GenderEnum;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
class UpdateWorkoutDateTest {

  final List<Workout> workouts = new ArrayList<>();
  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private UserDao userDao;


  @BeforeAll
  public void before() {
    userDao.deleteAll();
    userDao.saveAll(
        List.of(
            new UserApp(
                null,
                "chema",
                new BCryptPasswordEncoder().encode("chema69"),
                null,
                "Chema",
                "Garcia",
                "Romero",
                null,
                GenderEnum.MALE,
                ActivityLevelEnum.EXTREME),
            new UserApp(
                null,
                "alex",
                new BCryptPasswordEncoder().encode("alex69"),
                null,
                "Alex",
                "Garcia",
                "Fernandez",
                null,
                GenderEnum.FEMALE,
                ActivityLevelEnum.SEDENTARY),
            new UserApp(
                null,
                "adrian",
                new BCryptPasswordEncoder().encode("adrian69"),
                null,
                "Adrian",
                "Villar",
                "Gesto",
                null,
                GenderEnum.MALE,
                ActivityLevelEnum.MODERATE)));
  }

  @AfterAll
  public void afterAll() {
    userDao.deleteAll();
    workoutDao.deleteAll();
  }

  @BeforeEach
  void beforeEach() {
    final UserApp userApp = userDao.findByUsername("adrian");

    final Workout workout = new Workout(new Date(), null, userApp.getId(), new HashSet<>());
    workoutDao.save(workout);
    workouts.clear();
    workouts.add(workout);
  }

  @AfterEach
  void afterEach(){
    workoutDao.deleteById(workouts.get(0).getId());
    workouts.clear();
  }

  @Test
  @WithUserDetails("adrian")
  void deleteOneWorkout() throws Exception {
    final Workout workout = workouts.get(0);
    final UUID workoutId = workouts.get(0).getId();
    final String updateWorkoutDateRequest = """
{"date": "%s"}
""";

    mockMvc
        .perform(
            patch("/workout-api/workouts/" + workout.getId() + "/date")
                .contentType(APPLICATION_JSON)
                .content(String.format(updateWorkoutDateRequest, "2023-06-28")))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.date").value("2023-06-28"));

    final Optional<Workout> workoutDb = workoutDao.findById(workoutId);
    Assertions.assertTrue(workoutDb.isPresent());
    //Assertions.assertEquals("newDescription", workoutDb.get().getDescription());
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
