package org.avillar.gymtracker.authapi.exception.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.exception.application.RegisterException;
import org.avillar.gymtracker.common.errors.infrastructure.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@Order( value = Ordered.HIGHEST_PRECEDENCE )
public class AuthExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String MSG_ERROR_REGISTER_PROCESS_ERROR = "Error in the register process";
  private static final String MSG_AUTH_ERROR = "An error occurred during authentication";

  @ExceptionHandler(RegisterException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ApiError handleRegisterException(final RegisterException ex) {
    log.debug("Error in the registration process: {}", ex.getLocalizedMessage());
    return new ApiError(MSG_ERROR_REGISTER_PROCESS_ERROR, ex);
  }

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  protected ApiError handleIllegalAccessException(final AuthenticationException ex) {
    log.error("Error while trying to login:", ex);
    return new ApiError(MSG_AUTH_ERROR, ex);
  }
}
