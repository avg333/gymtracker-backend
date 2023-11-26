package org.avillar.gymtracker.usersapi.common.auth.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.UUID;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.avillar.gymtracker.common.errors.application.AuthOperations;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.usersapi.common.domain.Settings;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class AuthUsersServiceImplTest {

  @InjectMocks private AuthUsersServiceImpl authUsersService;

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
  void checkAccessOk() {
    final Settings settings = Instancio.create(Settings.class);
    settings.setUserId(getUserId());

    assertDoesNotThrow(() -> authUsersService.checkAccess(settings, AuthOperations.READ));
  }

  @ParameterizedTest
  @EnumSource(AuthOperations.class)
  void checkAccessKo(final AuthOperations ao) {
    final Settings settings = Instancio.create(Settings.class);

    final IllegalAccessException ex =
        assertThrows(
            IllegalAccessException.class, () -> authUsersService.checkAccess(settings, ao));
    assertEquals(settings.getId(), ex.getEntityId());
    assertEquals(getUserId(), ex.getCurrentUserId());
    assertEquals(Settings.class.getSimpleName(), ex.getEntityClassName());
    assertEquals(ao, ex.getAuthOperations());
  }

  private UUID getUserId() {
    final UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
    return userDetails.getId();
  }
}
