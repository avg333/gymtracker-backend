package org.avillar.gymtracker.authapi.common.exception.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.authapi.common.exception.application.UsernameAlreadyExistsException;
import org.avillar.gymtracker.authapi.common.exception.application.WrongRegisterCodeException;
import org.avillar.gymtracker.common.errors.infrastructure.model.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class AuthExceptionHandler {

  @ExceptionHandler({UsernameAlreadyExistsException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handleUsernameAlreadyExistsException(final UsernameAlreadyExistsException ex) {
    log.debug("Error in the registration process: {}", ex.getLocalizedMessage());
    return new ApiError("Username is already in use", ex);
  }

  @ExceptionHandler({WrongRegisterCodeException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handleWrongRegisterCodeException(final WrongRegisterCodeException ex) {
    log.debug("Error in the registration process: {}", ex.getLocalizedMessage());
    return new ApiError("Wrong registration code", ex);
  }

  @ExceptionHandler(AuthenticationException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public ApiError handleIllegalAccessException(final AuthenticationException ex) {
    log.warn("Error while trying to login:", ex);
    return new ApiError("Bad credentials", ex);
  }
}
