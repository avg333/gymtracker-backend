package org.avillar.gymtracker.workout.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.auth.application.AuthService;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.user.application.UserAppDto;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.avillar.gymtracker.workout.domain.Workout;
import org.avillar.gymtracker.workout.domain.WorkoutDao;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Calendar;
import java.util.Date;

import static org.avillar.gymtracker.errors.application.ErrorCodes.*;

@Component
public class WorkoutDtoValidator implements Validator {
    private static final int LONG_MAX_DESC = 255;
    private static final int MAX_DAYS_DATE_RANGE_FROM_TODAY = 365;

    private final WorkoutDao workoutDao;
    private final AuthService authService;
    private final UserDao userDao;

    public WorkoutDtoValidator(WorkoutDao workoutDao, AuthService authService, UserDao userDao) {
        this.workoutDao = workoutDao;
        this.authService = authService;
        this.userDao = userDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return WorkoutDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final WorkoutDto workoutDto = (WorkoutDto) target;
        final boolean exists = workoutDto.getId() != null && workoutDto.getId() > 0L;

        this.validateDate(workoutDto, errors);
        this.validateDescription(workoutDto, errors);

        if (exists) {
            this.validateAccess(workoutDto, errors);
        } else {
            this.validateUser(workoutDto, errors);
        }

        if (!errors.hasErrors()) {
            this.validateUniqueWorkoutDateForUser(workoutDto, errors);
        }
    }

    private void validateDate(final WorkoutDto workoutDto, final Errors errors) {
        final String fieldName = "date";
        final Date date = workoutDto.getDate();
        if (date == null) {
            errors.rejectValue(fieldName, ERR_BF_WORKOUT_20.name(), ERR_BF_WORKOUT_20.defaultMessage);
            return;
        }

        final Date currentDate = new Date();
        final Calendar c = Calendar.getInstance();

        c.setTime(currentDate);
        c.add(Calendar.DATE, MAX_DAYS_DATE_RANGE_FROM_TODAY);
        final Date maxDate = c.getTime();

        c.setTime(currentDate);
        c.add(Calendar.DATE, MAX_DAYS_DATE_RANGE_FROM_TODAY * -1);
        final Date minDate = c.getTime();

        if (date.after(maxDate)) {
            errors.rejectValue(fieldName, ERR_BF_WORKOUT_21.name(), ERR_BF_WORKOUT_21.defaultMessage);
        } else if (date.before(minDate)) {
            errors.rejectValue(fieldName, ERR_BF_WORKOUT_22.name(), ERR_BF_WORKOUT_22.defaultMessage);
        }
    }

    private void validateDescription(final WorkoutDto workoutDto, final Errors errors) {
        final String fieldName = "description";
        final String description = workoutDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, ERR_BF_CMM_20.name(), ERR_BF_CMM_20.defaultMessage);
        }
    }

    private void validateAccess(final WorkoutDto setDto, final Errors errors) {
        final String fieldName = "id";
        final Long workoutId = setDto.getId();
        if (!this.workoutDao.existsById(workoutId)) {
            errors.rejectValue(fieldName, ERR_BF_WORKOUT_04.name(), ERR_BF_WORKOUT_04.defaultMessage);
            return;
        }

        try {
            this.authService.checkAccess(this.workoutDao.getReferenceById(workoutId));
        } catch (IllegalAccessException e) {
            errors.rejectValue(fieldName, ERR_BF_WORKOUT_03.name(), ERR_BF_WORKOUT_03.defaultMessage);
        }
    }

    private void validateUser(final WorkoutDto workoutDto, final Errors errors) {
        final String fieldName = "user";
        final UserAppDto userAppDto = workoutDto.getUserApp();

        if (userAppDto == null || userAppDto.getId() == null || userAppDto.getId() <= 0L || !this.userDao.existsById(userAppDto.getId())) {
            errors.rejectValue(fieldName, ERR_BF_USER_04.name(), ERR_BF_USER_04.defaultMessage);
            return;
        }

        try {
            final UserApp userApp = new UserApp(userAppDto.getId());
            final Workout workout = new Workout();
            workout.setUserApp(userApp);
            this.authService.checkAccess(workout);
        } catch (IllegalAccessException e) {
            errors.rejectValue(fieldName, ERR_BF_WORKOUT_03.name(), ERR_BF_WORKOUT_03.defaultMessage);
        }
    }

    private void validateUniqueWorkoutDateForUser(final WorkoutDto workoutDto, final Errors errors) {
        final String fieldName = "date";
        final Date date = workoutDto.getDate();
        final UserApp userApp = new UserApp(workoutDto.getUserApp().getId());

        if (this.workoutDao.countByUserAppAndDate(userApp, date) > 0) {
            errors.rejectValue(fieldName, ERR_BF_WORKOUT_10.name(), ERR_BF_WORKOUT_10.defaultMessage);
        }
    }

}
