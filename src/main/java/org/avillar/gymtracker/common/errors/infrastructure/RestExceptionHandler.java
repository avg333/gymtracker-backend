package org.avillar.gymtracker.common.errors.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
@Order( value = Ordered.LOWEST_PRECEDENCE )
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
  
  private static final String MSG_NOT_FOUND_ERROR = "Entity not found";
  private static final String MSG_NOT_PERMISSION_ERROR =
      "You do not have permissions to access the resource";
  private static final String MSG_INTERNAL_SERVER_ERROR = "Internal server error";

  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  protected ApiError handleEntityNotFound(final EntityNotFoundException ex) {
    log.debug("Entity {} with id {} was not found", ex.getClassName(), ex.getId());
    return new ApiError(MSG_NOT_FOUND_ERROR, ex);
  }

  @ExceptionHandler(IllegalAccessException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  protected ApiError handleIllegalAccessException(final IllegalAccessException ex) {
    log.info(
        "Illegal access to entity {} with id {} by user with id {}",
        ex.getEntityClassName(),
        ex.getEntityId(),
        ex.getCurrentUserId());
    return new ApiError(MSG_NOT_PERMISSION_ERROR, ex);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ApiError handleException(final Exception ex) {
    log.error("Internal server error:", ex);
    return new ApiError(MSG_INTERNAL_SERVER_ERROR, ex);
  }
}
