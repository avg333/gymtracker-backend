package org.avillar.gymtracker.workoutapi.common.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.common.domain.Set;
import org.avillar.gymtracker.workoutapi.common.domain.SetGroup;
import org.avillar.gymtracker.workoutapi.common.domain.Workout;
import org.avillar.gymtracker.workoutapi.common.exception.application.WorkoutIllegalAccessException;
import org.instancio.Instancio;
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

  @InjectMocks private AuthWorkoutsServiceImpl authWorkoutsService;

  @Mock private Authentication auth;

  @BeforeEach
  void prepareSecurityContext() {
    SecurityContextHolder.clearContext();
    when(auth.getPrincipal())
        .thenReturn(
            new UserDetailsImpl(
                UUID.randomUUID(),
                Instancio.create(String.class),
                Instancio.create(String.class),
                null));
    when(auth.isAuthenticated()).thenReturn(true);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @Test
  void testCheckAccessWorkoutAccessAllowed() {
    final Workout workout = Instancio.create(Workout.class);
    workout.setUserId(getUserId());

    for (final AuthOperations ao : AuthOperations.values()) {
      assertDoesNotThrow(() -> authWorkoutsService.checkAccess(workout, ao));
    }
  }

  @Test
  void testCheckAccessSetGroupAccessAllowed() {
    final SetGroup setGroup = Instancio.create(SetGroup.class);
    setGroup.getWorkout().setUserId(getUserId());

    for (final AuthOperations ao : AuthOperations.values()) {
      assertDoesNotThrow(() -> authWorkoutsService.checkAccess(setGroup, ao));
    }
  }

  @Test
  void testCheckAccessSetAccessAllowed() {
    final Set set = Instancio.create(Set.class);
    set.getSetGroup().getWorkout().setUserId(getUserId());

    for (final AuthOperations ao : AuthOperations.values()) {
      assertDoesNotThrow(() -> authWorkoutsService.checkAccess(set, ao));
    }
  }

  @Test
  void testCheckAccessWorkoutAccessDenied() {
    final Workout workout = Instancio.create(Workout.class);

    for (final AuthOperations ao : AuthOperations.values()) {
      final WorkoutIllegalAccessException ex =
          assertThrows(
              WorkoutIllegalAccessException.class,
              () -> authWorkoutsService.checkAccess(workout, ao));
      assertException(workout, ao, ex);
    }
  }

  @Test
  void testCheckAccessSetGroupAccessDenied() {
    final SetGroup setGroup = Instancio.create(SetGroup.class);

    for (final AuthOperations ao : AuthOperations.values()) {
      final WorkoutIllegalAccessException ex =
          assertThrows(
              WorkoutIllegalAccessException.class,
              () -> authWorkoutsService.checkAccess(setGroup, ao));
      assertException(setGroup.getWorkout(), ao, ex);
    }
  }

  @Test
  void testCheckAccessSetAccessDenied() {
    final Set set = Instancio.create(Set.class);

    for (final AuthOperations ao : AuthOperations.values()) {
      final WorkoutIllegalAccessException ex =
          assertThrows(
              WorkoutIllegalAccessException.class, () -> authWorkoutsService.checkAccess(set, ao));
      assertException(set.getSetGroup().getWorkout(), ao, ex);
    }
  }

  private UUID getUserId() {
    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    return userDetails.getId();
  }

  private void assertException(
      final Workout workout, final AuthOperations ao, final WorkoutIllegalAccessException ex) {
    assertThat(ex).isNotNull();
    assertThat(ex.getEntityId()).isEqualTo(workout.getId());
    assertThat(ex.getCurrentUserId()).isEqualTo(getUserId());
    assertThat(ex.getEntityClassName()).isEqualTo(Workout.class.getSimpleName());
    assertThat(ex.getAuthOperations()).isEqualTo(ao);
  }
}
