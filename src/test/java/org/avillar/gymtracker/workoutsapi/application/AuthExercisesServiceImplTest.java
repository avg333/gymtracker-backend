package org.avillar.gymtracker.workoutsapi.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.auth.application.AuthWorkoutsServiceImpl;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class AuthExercisesServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();
  @InjectMocks private AuthWorkoutsServiceImpl authWorkoutsService;
  @Mock private Authentication auth;

  @Test
  void checkAccessWorkoutOk() {
    when(auth.getPrincipal())
        .thenReturn(new UserDetailsImpl(UUID.randomUUID(), "adrian", "adrian69", null));
    when(auth.isAuthenticated()).thenReturn(true);
    SecurityContextHolder.getContext().setAuthentication(auth);
    final Workout workout = easyRandom.nextObject(Workout.class);
    workout.setUserId(getUserId());
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(workout, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(workout, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(workout, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(workout, AuthOperations.READ));
  }

  @Test
  void checkAccessWorkoutKo() {
    when(auth.getPrincipal())
        .thenReturn(new UserDetailsImpl(UUID.randomUUID(), "adrian", "adrian69", null));
    when(auth.isAuthenticated()).thenReturn(true);
    SecurityContextHolder.getContext().setAuthentication(auth);
    final Workout workout = easyRandom.nextObject(Workout.class);
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(workout, AuthOperations.CREATE));
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(workout, AuthOperations.DELETE));
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(workout, AuthOperations.UPDATE));
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(workout, AuthOperations.READ));
  }

  @Test
  void testCheckAccessSetGroupOk() {
    when(auth.getPrincipal())
        .thenReturn(new UserDetailsImpl(UUID.randomUUID(), "adrian", "adrian69", null));
    when(auth.isAuthenticated()).thenReturn(true);
    SecurityContextHolder.getContext().setAuthentication(auth);
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    setGroup.getWorkout().setUserId(getUserId());
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(setGroup, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(setGroup, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(setGroup, AuthOperations.READ));
  }

  @Test
  void testCheckAccessSetGroupKo() {
    when(auth.getPrincipal())
        .thenReturn(new UserDetailsImpl(UUID.randomUUID(), "adrian", "adrian69", null));
    when(auth.isAuthenticated()).thenReturn(true);
    SecurityContextHolder.getContext().setAuthentication(auth);
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(setGroup, AuthOperations.CREATE));
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(setGroup, AuthOperations.DELETE));
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(setGroup, AuthOperations.UPDATE));
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(setGroup, AuthOperations.READ));
  }

  @Test
  void testCheckAccessSetOk() {
    when(auth.getPrincipal())
        .thenReturn(new UserDetailsImpl(UUID.randomUUID(), "adrian", "adrian69", null));
    when(auth.isAuthenticated()).thenReturn(true);
    SecurityContextHolder.getContext().setAuthentication(auth);
    final Set set = easyRandom.nextObject(Set.class);
    set.getSetGroup().getWorkout().setUserId(getUserId());
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(set, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(set, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(set, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authWorkoutsService.checkAccess(set, AuthOperations.READ));
  }

  @Test
  void testCheckAccessSetKo() {
    when(auth.getPrincipal())
        .thenReturn(new UserDetailsImpl(UUID.randomUUID(), "adrian", "adrian69", null));
    when(auth.isAuthenticated()).thenReturn(true);
    SecurityContextHolder.getContext().setAuthentication(auth);
    final Set set = easyRandom.nextObject(Set.class);
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(set, AuthOperations.CREATE));
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(set, AuthOperations.DELETE));
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(set, AuthOperations.UPDATE));
    assertThrows(
        IllegalAccessException.class,
        () -> authWorkoutsService.checkAccess(set, AuthOperations.READ));
  }

  @Test
  void testNoUser(){
    SecurityContextHolder.clearContext();
    assertThrows(
        RuntimeException.class,
        () -> authWorkoutsService.checkAccess(easyRandom.nextObject(Workout.class), AuthOperations.READ));
    assertThrows(
        RuntimeException.class,
        () -> authWorkoutsService.checkAccess(easyRandom.nextObject(SetGroup.class), AuthOperations.READ));
    assertThrows(
        RuntimeException.class,
        () -> authWorkoutsService.checkAccess(easyRandom.nextObject(Set.class), AuthOperations.READ));
  }

  private UUID getUserId() {
    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    return userDetails.getId();
  }
}
