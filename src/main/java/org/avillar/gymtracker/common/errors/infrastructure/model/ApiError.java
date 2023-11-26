package org.avillar.gymtracker.common.errors.infrastructure.model;

import static org.avillar.gymtracker.workoutapi.common.utils.Constants.ISO_8601_DATE_FORMAT;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Getter
@Slf4j
public class ApiError {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ISO_8601_DATE_FORMAT)
  private final LocalDateTime timestamp = LocalDateTime.now();

  private final String message;
  private final String debugMessage;

  private List<ValidationError> validationErrors;

  public ApiError(final String message, final Exception exception) {
    boolean existsException =
        exception != null && StringUtils.isNotEmpty(exception.getLocalizedMessage());

    this.message = message;
    this.debugMessage =
        log.isDebugEnabled() && existsException ? exception.getLocalizedMessage() : null;
  }

  public ApiError(
      final String message,
      final Exception exception,
      final List<ValidationError> validationErrors) {
    boolean existsException =
        exception != null && StringUtils.isNotEmpty(exception.getLocalizedMessage());

    this.message = message;
    this.debugMessage =
        log.isDebugEnabled() && existsException ? exception.getLocalizedMessage() : null;
    this.validationErrors = validationErrors;
  }

  public record ValidationError(String field, String message) {}
}
