package org.avillar.gymtracker.workoutapi.setgroup.deletesetgroup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.authapi.auth.domain.UserApp;
import org.avillar.gymtracker.authapi.auth.domain.UserDao;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetDao;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.SetGroupDao;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.avillar.gymtracker.workoutapi.domain.WorkoutDao;
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
class DeleteSetGroupTest {

  final List<Workout> workouts = new ArrayList<>();
  @Autowired private MockMvc mockMvc;
  @Autowired private WorkoutDao workoutDao;
  @Autowired private SetGroupDao setGroupDao;
  @Autowired private SetDao setDao;
  @Autowired private UserDao userDao;

  @BeforeEach
  void beforeEach() {
    final UserApp userApp = userDao.findByUsername("adrian");

    final Workout workout = new Workout(new Date(), null, userApp.getId(), new HashSet<>());
    workoutDao.save(workout);
    final SetGroup setGroup = new SetGroup(null,UUID.randomUUID(),workout, new HashSet<>());
    setGroup.setListOrder(0);
    setGroupDao.save(setGroup);
    workout.getSetGroups().add(setGroup);
    final Set set = new Set(null, 1, 1.0, 1.0, setGroup);
    set.setListOrder(0);
    setDao.save(set);
    setGroup.getSets().add(set);
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
    final SetGroup setGroup = workout.getSetGroups().stream().findAny().get();
    final Set set = setGroup.getSets().stream().findAny().get();
    final UUID workoutId = workouts.get(0).getId();
    mockMvc
        .perform(get("/org.avillar.gymtracker.workoutapi.workout-api/workouts/" + workout.getId()))
        .andDo(print())
        .andExpect(status().isOk());
    mockMvc
        .perform(get("/org.avillar.gymtracker.workoutapi.workout-api/setGroups/" + setGroup.getId()))
        .andDo(print())
        .andExpect(status().isOk());
    mockMvc
        .perform(get("/org.avillar.gymtracker.workoutapi.workout-api/sets/" + set.getId()))
        .andDo(print())
        .andExpect(status().isOk());
    mockMvc
        .perform(delete("/org.avillar.gymtracker.workoutapi.workout-api/workouts/" + workoutId))
        .andDo(print())
        .andExpect(status().isNoContent());
    mockMvc
        .perform(get("/org.avillar.gymtracker.workoutapi.workout-api/workouts/" + workoutId))
        .andDo(print())
        .andExpect(status().isNotFound());
    mockMvc
        .perform(get("/org.avillar.gymtracker.workoutapi.workout-api/setGroups/" + setGroup.getId()))
        .andDo(print())
        .andExpect(status().isNotFound());
    mockMvc
        .perform(get("/org.avillar.gymtracker.workoutapi.workout-api/sets/" + set.getId()))
        .andDo(print())
        .andExpect(status().isNotFound());
  }

  @Test
  @WithUserDetails("chema")
  void deleteNotFoundAndNotPermission() throws Exception {
    final Workout workout = workouts.get(0);
    mockMvc
        .perform(get("/org.avillar.gymtracker.workoutapi.workout-api/workouts/" +UUID.randomUUID()))
        .andDo(print())
        .andExpect(status().isNotFound());
    mockMvc
        .perform(get("/org.avillar.gymtracker.workoutapi.workout-api/workouts/" + workout.getId()))
        .andDo(print())
        .andExpect(status().isForbidden());
  }
}