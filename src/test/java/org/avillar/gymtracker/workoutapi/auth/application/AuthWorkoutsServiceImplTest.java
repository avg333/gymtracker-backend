package org.avillar.gymtracker.workoutapi.auth.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.workoutapi.domain.Set;
import org.avillar.gymtracker.workoutapi.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class AuthWorkoutsServiceImplTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private AuthWorkoutsServiceImpl authWorkoutsService;

  @Mock private Authentication auth;

  @BeforeEach
  void beforeEach() {
    SecurityContextHolder.clearContext();
    when(auth.getPrincipal())
        .thenReturn(
            new UserDetailsImpl(
                UUID.randomUUID(),
                easyRandom.nextObject(String.class),
                easyRandom.nextObject(String.class),
                null));
    when(auth.isAuthenticated()).thenReturn(true);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @Test
  void checkAccessWorkoutOk() {
    final Workout workout = easyRandom.nextObject(Workout.class);
    workout.setUserId(getUserId());

    for (final AuthOperations ao : AuthOperations.values()) {
      assertDoesNotThrow(() -> authWorkoutsService.checkAccess(workout, ao));
    }
  }

  @Test
  void checkAccessWorkoutKo() {
    final Workout workout = easyRandom.nextObject(Workout.class);

    for (final AuthOperations ao : AuthOperations.values()) {
      assertThrow(workout, ao);
    }
  }

  @Test
  void testCheckAccessSetGroupOk() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);
    setGroup.getWorkout().setUserId(getUserId());

    for (final AuthOperations ao : AuthOperations.values()) {
      assertDoesNotThrow(() -> authWorkoutsService.checkAccess(setGroup, ao));
    }
  }

  @Test
  void testCheckAccessSetGroupKo() {
    final SetGroup setGroup = easyRandom.nextObject(SetGroup.class);

    for (final AuthOperations ao : AuthOperations.values()) {
      assertThrow(setGroup, ao);
    }
  }

  @Test
  void testCheckAccessSetOk() {
    final Set set = easyRandom.nextObject(Set.class);
    set.getSetGroup().getWorkout().setUserId(getUserId());

    for (final AuthOperations ao : AuthOperations.values()) {
      assertDoesNotThrow(() -> authWorkoutsService.checkAccess(set, ao));
    }
  }

  @Test
  void testCheckAccessSetKo() {
    final Set set = easyRandom.nextObject(Set.class);

    for (final AuthOperations ao : AuthOperations.values()) {
      assertThrow(set, ao);
    }
  }

  private UUID getUserId() {
    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    return userDetails.getId();
  }

  private void assertThrow(final Workout workout, final AuthOperations ao) {
    final IllegalAccessException ex =
        assertThrows(
            IllegalAccessException.class, () -> authWorkoutsService.checkAccess(workout, ao));
    assertEquals(workout.getId(), ex.getEntityId());
    assertEquals(getUserId(), ex.getCurrentUserId());
    assertEquals(Workout.class.getSimpleName(), ex.getEntityClassName());
    assertEquals(ao, ex.getAuthOperations());
  }

  private void assertThrow(final SetGroup setGroup, final AuthOperations ao) {
    final IllegalAccessException ex =
        assertThrows(
            IllegalAccessException.class, () -> authWorkoutsService.checkAccess(setGroup, ao));
    assertEquals(setGroup.getWorkout().getId(), ex.getEntityId());
    assertEquals(getUserId(), ex.getCurrentUserId());
    assertEquals(Workout.class.getSimpleName(), ex.getEntityClassName());
    assertEquals(ao, ex.getAuthOperations());
  }

  private void assertThrow(final Set set, final AuthOperations ao) {
    final IllegalAccessException ex =
        assertThrows(IllegalAccessException.class, () -> authWorkoutsService.checkAccess(set, ao));
    assertEquals(set.getSetGroup().getWorkout().getId(), ex.getEntityId());
    assertEquals(getUserId(), ex.getCurrentUserId());
    assertEquals(Workout.class.getSimpleName(), ex.getEntityClassName());
    assertEquals(ao, ex.getAuthOperations());
  }
}
