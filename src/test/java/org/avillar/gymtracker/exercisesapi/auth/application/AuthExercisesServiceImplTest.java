package org.avillar.gymtracker.exercisesapi.auth.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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
  void checkAccessOwner() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setOwner(getUserId());
    for (AccessTypeEnum at : AccessTypeEnum.values()) {
      exercise.setAccessType(at);
      for (AuthOperations ao : AuthOperations.values()) {
        assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, ao));
      }
    }
  }

  @Test
  void checkAccessNotOwnerPrivate() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setAccessType(AccessTypeEnum.PRIVATE);

    for (AuthOperations ao : AuthOperations.values()) {
      final IllegalAccessException ex =
          assertThrows(
              IllegalAccessException.class, () -> authExercisesService.checkAccess(exercise, ao));
      assertEquals(exercise.getId(), ex.getEntityId());
      assertEquals(getUserId(), ex.getCurrentUserId());
      assertEquals(Exercise.class.getSimpleName(), ex.getEntityClassName());
      assertEquals(ao, ex.getAuthOperations());
    }
  }

  @Test
  void checkAccessNotOwnerPublic() {
    final Exercise exercise = easyRandom.nextObject(Exercise.class);
    exercise.setAccessType(AccessTypeEnum.PUBLIC);

    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.READ));

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
  }

  private UUID getUserId() {
    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    return userDetails.getId();
  }
}
