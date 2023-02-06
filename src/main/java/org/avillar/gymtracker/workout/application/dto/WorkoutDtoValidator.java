package org.avillar.gymtracker.workout.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.user.application.UserAppDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

public class WorkoutDtoValidator implements Validator {
    private static final int LONG_MAX_DESC = 255;

    private static void validateDate(final WorkoutDto workoutDto, final Errors errors) {
        final String fieldName = "date";
        final Date date = workoutDto.getDate();
        if (date == null) {
            errors.rejectValue(fieldName, "field.date.notValid", "La fecha del workout no es valida");
        } //TODO Añadir fecha maxima y minima
        //TODO Validar si ya existe un WO en esa date
    }

    private static void validateDescription(final WorkoutDto workoutDto, final Errors errors) {
        final String fieldName = "description";
        final String description = workoutDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, "field.description.max", "La longitud de la descripcion de la set es superior a la maxima");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return WorkoutDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final WorkoutDto workoutDto = (WorkoutDto) target;

        validateDate(workoutDto, errors);
        validateDescription(workoutDto, errors);
        validateUser(workoutDto, errors);
    }

    private void validateUser(final WorkoutDto workoutDto, final Errors errors) {
        final String fieldName = "user";
        final UserAppDto userAppDto = workoutDto.getUserApp();
        final Long workoutId = workoutDto.getId();
        if (workoutId != null && workoutId > 0L) {
            return;
        }

        if (userAppDto == null || userAppDto.getId() == null || userAppDto.getId() <= 0L) {
            errors.rejectValue(fieldName, "field.user.notExists", "El usuario especificado no existe");
        }
    }
}
