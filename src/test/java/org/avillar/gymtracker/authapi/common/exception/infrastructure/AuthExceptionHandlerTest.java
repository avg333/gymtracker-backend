package org.avillar.gymtracker.authapi.common.exception.infrastructure;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.common.exception.application.UsernameAlreadyExistsException;
import org.avillar.gymtracker.authapi.common.exception.application.WrongRegisterCodeException;
import org.avillar.gymtracker.common.errors.infrastructure.model.ApiError;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

@Slf4j
@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class AuthExceptionHandlerTest {

  @InjectMocks private AuthExceptionHandler restExceptionHandler;

  private static void checkResult(final ApiError result, final String msg, final Date now) {
    assertThat(result).isNotNull();
    assertThat(result.getMessage()).isEqualTo(msg);
    assertThat(result.getValidationErrors()).isNull();
    if (log.isDebugEnabled()) {
      assertThat(result.getDebugMessage()).isNotNull();
    } else {
      assertThat(result.getDebugMessage()).isNull();
    }

    final LocalDateTime nowLocalDateTime =
        now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    assertThat(result.getTimestamp())
        .isNotNull()
        .isBetween(nowLocalDateTime.minusSeconds(5), nowLocalDateTime.plusSeconds(5));
  }

  @Test
  void shouldReturnBadRequestWhenUsernameAlreadyExistsExceptionIsThrown() {
    final Date now = new Date();
    final String msg = "Username is already in use";
    final UsernameAlreadyExistsException ex = new UsernameAlreadyExistsException();
    checkResult(restExceptionHandler.handleUsernameAlreadyExistsException(ex), msg, now);
  }

  @Test
  void shouldReturnBadRequestWhenWrongRegisterCodeExceptionIsThrown() {
    final Date now = new Date();
    final String msg = "Wrong registration code";
    final WrongRegisterCodeException ex = new WrongRegisterCodeException();
    checkResult(restExceptionHandler.handleWrongRegisterCodeException(ex), msg, now);
  }

  @Test
  void shouldReturnForbiddenWhenUsernameAlreadyExistsExceptionIsThrown() {
    final Date now = new Date();
    final String msg = "Bad credentials";
    final AuthenticationException ex = new NoOpenPortAuthenticationException();
    checkResult(restExceptionHandler.handleIllegalAccessException(ex), msg, now);
  }

  private static class NoOpenPortAuthenticationException extends AuthenticationException {
    public NoOpenPortAuthenticationException() {
      super(Instancio.create(String.class));
    }
  }
}
