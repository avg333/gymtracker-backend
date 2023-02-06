package org.avillar.gymtracker.workout.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.user.application.UserAppDto;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.avillar.gymtracker.workout.domain.WorkoutDao;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Date;

@Component
public class WorkoutDtoValidator implements Validator {
    private static final int LONG_MAX_DESC = 255;

    private final UserDao userDao;
    private final WorkoutDao workoutDao;

    public WorkoutDtoValidator(UserDao userDao,
                               WorkoutDao workoutDao) {
        this.userDao = userDao;
        this.workoutDao = workoutDao;
    }

    private static void validateDescription(final WorkoutDto workoutDto, final Errors errors) {
        final String fieldName = "description";
        final String description = workoutDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, "102", "La longitud es excesiva");
        }
    }

    private static void validateDate(final WorkoutDto workoutDto, final Errors errors) {
        final String fieldName = "date";
        final Date date = workoutDto.getDate();
        if (date == null) {
            errors.rejectValue(fieldName, "103", "La fecha del workout no es valida");
        } //TODO Añadir fecha maxima y minima
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
        if (workoutDto.getId() == null) {
            validateUser(workoutDto, errors);
        }
    }

    private void validateUser(final WorkoutDto workoutDto, final Errors errors) {
        final String fieldName = "user";
        final UserAppDto userAppDto = workoutDto.getUserApp();
        if (userAppDto == null || userAppDto.getId() == null || !this.userDao.existsById(userAppDto.getId())) {
            throw new EntityNotFoundException(UserApp.class, userAppDto != null && userAppDto.getId() != null ? userAppDto.getId() : 0L);
        }
    }
}
