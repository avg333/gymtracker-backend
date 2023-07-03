package org.avillar.gymtracker.exercisesapi.auth.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.avillar.gymtracker.common.errors.application.AccessType;
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
        .thenReturn(new UserDetailsImpl(UUID.randomUUID(), "adrian", "adrian69", null));
    when(auth.isAuthenticated()).thenReturn(true);
    SecurityContextHolder.getContext().setAuthentication(auth);
  }

  @Test
  void checkAccessOk() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setAccessType(AccessType.PUBLIC);
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.READ));
    exercise.setOwner(getUserId());
    exercise.setAccessType(AccessType.PRIVATE);
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.READ));
    exercise.setAccessType(AccessType.PUBLIC);
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.READ));
  }

  @Test
  void checkAccessKo() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setAccessType(AccessType.PRIVATE);
    assertThrows(
        IllegalAccessException.class,
        () -> authExercisesService.checkAccess(exercise, AuthOperations.CREATE));
    assertThrows(
        IllegalAccessException.class,
        () -> authExercisesService.checkAccess(exercise, AuthOperations.DELETE));
    assertThrows(
        IllegalAccessException.class,
        () -> authExercisesService.checkAccess(exercise, AuthOperations.UPDATE));
    assertThrows(
        IllegalAccessException.class,
        () -> authExercisesService.checkAccess(exercise, AuthOperations.READ));
  }

  @Test
  void testCheckAccessAllOk() {
    final Exercise exercise1 = easyRandom.nextObject(Exercise.class);
    final Exercise exercise2 = easyRandom.nextObject(Exercise.class);
    final List<Exercise> exercises = List.of(exercise1, exercise2);
    exercises.forEach(exercise -> exercise.setAccessType(AccessType.PUBLIC));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.READ));
    exercises.forEach(exercise -> exercise.setOwner(getUserId()));
    exercises.forEach(exercise -> exercise.setAccessType(AccessType.PRIVATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.CREATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.DELETE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.UPDATE));
    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercises, AuthOperations.READ));
    exercises.forEach(exercise -> exercise.setAccessType(AccessType.PUBLIC));
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
    exercises.forEach(exercise -> exercise.setAccessType(AccessType.PRIVATE));
    assertThrows(
        IllegalAccessException.class,
        () -> authExercisesService.checkAccess(exercises, AuthOperations.CREATE));
    assertThrows(
        IllegalAccessException.class,
        () -> authExercisesService.checkAccess(exercises, AuthOperations.DELETE));
    assertThrows(
        IllegalAccessException.class,
        () -> authExercisesService.checkAccess(exercises, AuthOperations.UPDATE));
    assertThrows(
        IllegalAccessException.class,
        () -> authExercisesService.checkAccess(exercises, AuthOperations.READ));
  }

  private UUID getUserId() {
    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    return userDetails.getId();
  }
}
