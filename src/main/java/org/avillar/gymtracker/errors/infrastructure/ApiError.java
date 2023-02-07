package org.avillar.gymtracker.errors.infrastructure;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ApiError {
    private final HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    public ApiError(HttpStatus status) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
    }

    private void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    private void addValidationError(String objectName, String field, String errorCode, String defaultMessage) {
        addSubError(new ApiValidationError(objectName, field, errorCode, defaultMessage));
    }

    private void addValidationError(String objectName, ObjectError objectError) {
        this.addValidationError(
                objectName,
                ((FieldError) objectError).getField(),
                objectError.getCode(),
                objectError.getDefaultMessage());
    }

    public void addValidationError(String objectName, List<ObjectError> globalErrors) {
        globalErrors.forEach(glo -> this.addValidationError(objectName, glo));
    }
}
