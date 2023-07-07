package org.avillar.gymtracker.workoutapi.workout.getworkoutwithsetgroups;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserApp.ActivityLevelEnum;
import org.avillar.gymtracker.authapi.domain.UserApp.GenderEnum;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
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
class GetWorkoutWithSetGroupsTest {

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
    workouts.add(workout);
  }

  @AfterEach
  void afterEach(){
    workoutDao.deleteById(workouts.get(0).getId());
    workouts.clear();
  }

  @Test
  @WithUserDetails("adrian")
  void getOneWorkout() throws Exception {
    final Workout workout = workouts.get(0);
    final UUID workoutId = workouts.get(0).getId();

    mockMvc
        .perform(
            get("/workout-api/workouts/" + workout.getId()))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(workoutId.toString()));
  }

  @Test
  @WithUserDetails("chema")
  void getNotFoundAndNotPermission() throws Exception {
    final Workout workout = workouts.get(0);
    final UUID workoutId = workouts.get(0).getId();
    mockMvc
        .perform(
            get("/workout-api/workouts/" + UUID.randomUUID()))
        .andDo(print())
        .andExpect(status().isNotFound());
    mockMvc
        .perform(
            get("/workout-api/workouts/" + workoutId))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
}
