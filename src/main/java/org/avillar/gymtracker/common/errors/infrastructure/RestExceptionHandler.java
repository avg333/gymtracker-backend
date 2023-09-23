package org.avillar.gymtracker.common.errors.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.avillar.gymtracker.common.errors.application.exceptions.EntityNotFoundException;
import org.avillar.gymtracker.common.errors.application.exceptions.IllegalAccessException;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order()
public class RestExceptionHandler {

  private static final String MSG_NOT_FOUND_ERROR = "Entity not found";
  private static final String MSG_NOT_PERMISSION_ERROR =
      "You do not have permissions to access the resource";
  private static final String MSG_REDIS_CONNECTION_ERROR = "Redis connection error";
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

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ApiError handleValidationErrors(final MethodArgumentNotValidException ex) {
    log.debug("Validation error: {}", ex.getLocalizedMessage());
    return new ApiError(
        ex.getBody().getDetail(),
        ex,
        ex.getBindingResult().getFieldErrors().stream()
            .map(
                fieldError ->
                    new ApiError.ValidationError(
                        fieldError.getField(), fieldError.getDefaultMessage()))
            .toList());
  } // TODO Test this

  @ExceptionHandler(RedisConnectionFailureException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ApiError handleRedisConnectionException(final RedisConnectionFailureException ex) {
    log.error("Redis connection error:", ex);
    return new ApiError(MSG_REDIS_CONNECTION_ERROR, ex);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  protected ApiError handleException(final Exception ex) {
    log.error("Internal server error:", ex);
    return new ApiError(MSG_INTERNAL_SERVER_ERROR, ex);
  }
}
