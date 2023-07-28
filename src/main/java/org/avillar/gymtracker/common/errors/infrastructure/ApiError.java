package org.avillar.gymtracker.common.errors.infrastructure;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
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

  public ApiError(final String message, final Exception exception) {
    boolean existsException =
        exception != null && StringUtils.isNotEmpty(exception.getLocalizedMessage());

    this.message = message;
    this.debugMessage =
        log.isDebugEnabled() && existsException ? exception.getLocalizedMessage() : null;
  }
}
