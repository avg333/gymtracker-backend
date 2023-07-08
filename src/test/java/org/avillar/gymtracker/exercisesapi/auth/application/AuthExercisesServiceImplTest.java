package org.avillar.gymtracker.exercisesapi.auth.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.domain.Exercise;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.BeforeEach;
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

  @InjectMocks private AuthExercisesServiceImpl authExercisesService;

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
  void checkAccessOk() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setAccessType(AccessTypeEnum.PUBLIC);
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.READ));
    exercise.setOwner(getUserId());
    exercise.setAccessType(AccessTypeEnum.PRIVATE);
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.READ));
    exercise.setAccessType(AccessTypeEnum.PUBLIC);
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.READ));
  }

  @Test
  void checkAccessKo() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setAccessType(AccessTypeEnum.PRIVATE);
    final IllegalAccessException createException =
        assertThrows(
            IllegalAccessException.class,
            () -> authExercisesService.checkAccess(exercise, AuthOperations.CREATE));
    final IllegalAccessException deleteException =
        assertThrows(
            IllegalAccessException.class,
            () -> authExercisesService.checkAccess(exercise, AuthOperations.DELETE));
    final IllegalAccessException updateException =
        assertThrows(
            IllegalAccessException.class,
            () -> authExercisesService.checkAccess(exercise, AuthOperations.UPDATE));
    final IllegalAccessException readException =
        assertThrows(
            IllegalAccessException.class,
            () -> authExercisesService.checkAccess(exercise, AuthOperations.READ));

    assertEquals(exercise.getId(), createException.getEntityId());
    assertEquals(getUserId(), createException.getCurrentUserId());
    assertEquals(Exercise.class.getSimpleName(), createException.getEntityClassName());
    assertEquals(AuthOperations.CREATE, createException.getAuthOperations());

    assertEquals(exercise.getId(), deleteException.getEntityId());
    assertEquals(getUserId(), deleteException.getCurrentUserId());
    assertEquals(Exercise.class.getSimpleName(), deleteException.getEntityClassName());
    assertEquals(AuthOperations.DELETE, deleteException.getAuthOperations());

    assertEquals(exercise.getId(), updateException.getEntityId());
    assertEquals(getUserId(), updateException.getCurrentUserId());
    assertEquals(Exercise.class.getSimpleName(), updateException.getEntityClassName());
    assertEquals(AuthOperations.UPDATE, updateException.getAuthOperations());

    assertEquals(exercise.getId(), readException.getEntityId());
    assertEquals(getUserId(), readException.getCurrentUserId());
    assertEquals(Exercise.class.getSimpleName(), readException.getEntityClassName());
    assertEquals(AuthOperations.READ, readException.getAuthOperations());
  }

  @Test
  void testCheckAccessAllOk() {
    final Exercise exercise1 = easyRandom.nextObject(Exercise.class);
    final Exercise exercise2 = easyRandom.nextObject(Exercise.class);
    final List<Exercise> exercises = List.of(exercise1, exercise2);
    exercises.forEach(exercise -> exercise.setAccessType(AccessTypeEnum.PUBLIC));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.READ));
    exercises.forEach(exercise -> exercise.setOwner(getUserId()));
    exercises.forEach(exercise -> exercise.setAccessType(AccessTypeEnum.PRIVATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.READ));
    exercises.forEach(exercise -> exercise.setAccessType(AccessTypeEnum.PUBLIC));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.READ));
  }

  @Test
  void testCheckAccessOneKo() {
    final Exercise exercise1 = easyRandom.nextObject(Exercise.class);
    final Exercise exercise2 = easyRandom.nextObject(Exercise.class);
    final List<Exercise> exercises = List.of(exercise1, exercise2);
    exercise1.setOwner(getUserId());
    exercises.forEach(exercise -> exercise.setAccessType(AccessTypeEnum.PRIVATE));
    final IllegalAccessException createException =
        assertThrows(
            IllegalAccessException.class,
            () -> authExercisesService.checkAccess(exercises, AuthOperations.CREATE));
    final IllegalAccessException deleteException =
        assertThrows(
            IllegalAccessException.class,
            () -> authExercisesService.checkAccess(exercises, AuthOperations.DELETE));
    final IllegalAccessException updateException =
        assertThrows(
            IllegalAccessException.class,
            () -> authExercisesService.checkAccess(exercises, AuthOperations.UPDATE));
    final IllegalAccessException readException =
        assertThrows(
            IllegalAccessException.class,
            () -> authExercisesService.checkAccess(exercises, AuthOperations.READ));

    assertEquals(exercise2.getId(), createException.getEntityId());
    assertEquals(getUserId(), createException.getCurrentUserId());
    assertEquals(Exercise.class.getSimpleName(), createException.getEntityClassName());
    assertEquals(AuthOperations.CREATE, createException.getAuthOperations());

    assertEquals(exercise2.getId(), deleteException.getEntityId());
    assertEquals(getUserId(), deleteException.getCurrentUserId());
    assertEquals(Exercise.class.getSimpleName(), deleteException.getEntityClassName());
    assertEquals(AuthOperations.DELETE, deleteException.getAuthOperations());

    assertEquals(exercise2.getId(), updateException.getEntityId());
    assertEquals(getUserId(), updateException.getCurrentUserId());
    assertEquals(Exercise.class.getSimpleName(), updateException.getEntityClassName());
    assertEquals(AuthOperations.UPDATE, updateException.getAuthOperations());

    assertEquals(exercise2.getId(), readException.getEntityId());
    assertEquals(getUserId(), readException.getCurrentUserId());
    assertEquals(Exercise.class.getSimpleName(), readException.getEntityClassName());
    assertEquals(AuthOperations.READ, readException.getAuthOperations());
  }

  private UUID getUserId() {
    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    return userDetails.getId();
  }
}
