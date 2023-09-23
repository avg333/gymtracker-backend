package org.avillar.gymtracker.common.errors.infrastructure;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Getter
@Slf4j
public class ApiError {

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
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

  @Data
  public static class ValidationError {
    private final String field;
    private final String message;

    public ValidationError(String field, String message) {
      this.field = field;
      this.message = message;
    }
  }
}
