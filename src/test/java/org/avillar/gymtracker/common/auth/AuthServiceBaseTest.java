package org.avillar.gymtracker.common.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.workoutapi.domain.Workout;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class AuthServiceBaseTest {

  private final EasyRandom easyRandom = new EasyRandom();

  private final AuthServiceBase authServiceBase = new AuthServiceBase() {};

  @Mock private Authentication auth;

  @Test
  void getLoggedUserIdWithUser() {
    final UUID expected = UUID.randomUUID();

    when(auth.getPrincipal()).thenReturn(new UserDetailsImpl(expected, "adrian", "adrian69", null));
    when(auth.isAuthenticated()).thenReturn(true);
    SecurityContextHolder.getContext().setAuthentication(auth);

    final UUID result = assertDoesNotThrow(authServiceBase::getLoggedUserId);
    assertEquals(expected, result);
  }

  @Test
  void getLoggedUserIdWithNoUser() {
    SecurityContextHolder.clearContext();
    assertThrows(SecurityException.class, authServiceBase::getLoggedUserId);
  }

  @Test
  void checkParametersOk() {
    assertDoesNotThrow(
        () ->
            authServiceBase.checkParameters(
                easyRandom.nextObject(Workout.class), AuthOperations.READ));
  }

  @Test
  void checkParametersNullAuthOperation() {
    assertThrows(
        IllegalArgumentException.class,
        () -> authServiceBase.checkParameters(easyRandom.nextObject(Workout.class), null));
  }

  @Test
  void checkParametersNullEntity() {
    assertThrows(
        IllegalArgumentException.class,
        () -> authServiceBase.checkParameters(null, AuthOperations.READ));
  }
}
