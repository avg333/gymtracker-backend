package org.avillar.gymtracker.authapi.exception.infrastructure;

import static org.junit.jupiter.api.Assertions.*;

import org.avillar.gymtracker.authapi.exception.application.RegisterException;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.AuthenticationException;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class AuthExceptionHandlerTest {

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private AuthExceptionHandler restExceptionHandler;

  @Test
  void testHandleRegisterException() {
    assertEquals(
        "Error in the register process",
        restExceptionHandler
            .handleRegisterException(new RegisterException(easyRandom.nextObject(String.class)))
            .getMessage());
  }

  @Test
  void testHandleIllegalAccessException() {
    class NoOpenPortAuthenticationException extends AuthenticationException {
      public NoOpenPortAuthenticationException(String msg) {
        super(msg);
      }
    }
    assertEquals(
        "An error occurred during authentication",
        restExceptionHandler
            .handleIllegalAccessException(
                new NoOpenPortAuthenticationException(easyRandom.nextObject(String.class)))
            .getMessage());
  }
}
