package org.avillar.gymtracker.workoutapi.errors.infrastructure;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiError {
  private final HttpStatus status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
  private final LocalDateTime timestamp = LocalDateTime.now();

  private String message;
  private String debugMessage;

  public ApiError(final HttpStatus status) {
    this.status = status;
  }
}
