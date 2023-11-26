package org.avillar.gymtracker.exercisesapi.common.auth.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.avillar.gymtracker.common.errors.application.AccessTypeEnum;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.exercisesapi.common.domain.Exercise;
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
class AuthExercisesServiceImplTest {

  @InjectMocks private AuthExercisesServiceImpl authExercisesService;

  @Mock private Authentication auth;

  @BeforeEach
  void beforeEach() {
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
  void checkAccessOwnerPrivate() {
    final Exercise exercise = Instancio.create(Exercise.class);
    exercise.setAccessType(AccessTypeEnum.PRIVATE);
    exercise.setOwner(getUserId());
    for (final AuthOperations ao : AuthOperations.values()) {
      assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, ao));
    }
  }

  @Test
  void checkAccessOwnerPublic() {
    final Exercise exercise = Instancio.create(Exercise.class);
    exercise.setAccessType(AccessTypeEnum.PUBLIC);
    exercise.setOwner(getUserId());

    for (final AuthOperations ao : AuthOperations.values()) {
      if (ao.equals(AuthOperations.READ)) {
        assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, ao));
      } else {
        assertThrow(exercise, ao);
      }
    }
  }

  @Test
  void checkAccessNotOwnerPrivate() {
    final Exercise exercise = Instancio.create(Exercise.class);
    exercise.setAccessType(AccessTypeEnum.PRIVATE);

    for (final AuthOperations ao : AuthOperations.values()) {
      assertThrow(exercise, ao);
    }
  }

  @Test
  void checkAccessNotOwnerPublic() {
    final Exercise exercise = Instancio.create(Exercise.class);
    exercise.setAccessType(AccessTypeEnum.PUBLIC);

    assertDoesNotThrow(() -> authExercisesService.checkAccess(exercise, AuthOperations.READ));
    assertThrow(exercise, AuthOperations.CREATE);
    assertThrow(exercise, AuthOperations.DELETE);
    assertThrow(exercise, AuthOperations.UPDATE);
  }

  private UUID getUserId() {
    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    return userDetails.getId();
  }

  private void assertThrow(Exercise exercise, AuthOperations ao) {
    final IllegalAccessException ex =
        assertThrows(
            IllegalAccessException.class, () -> authExercisesService.checkAccess(exercise, ao));
    assertEquals(exercise.getId(), ex.getEntityId());
    assertEquals(getUserId(), ex.getCurrentUserId());
    assertEquals(Exercise.class.getSimpleName(), ex.getEntityClassName());
    assertEquals(ao, ex.getAuthOperations());
  }
}
