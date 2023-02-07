package org.avillar.gymtracker.setgroup.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.auth.application.AuthService;
import org.avillar.gymtracker.errors.application.IllegalAccessException;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.exercise.domain.ExerciseDao;
import org.avillar.gymtracker.session.application.dto.SessionDto;
import org.avillar.gymtracker.session.domain.SessionDao;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;
import org.avillar.gymtracker.workout.domain.WorkoutDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.avillar.gymtracker.errors.application.ErrorCodes.*;

@Component
public class SetGroupDtoValidator implements Validator {
    private static final int LONG_MAX_DESC = 255;

    private final SetGroupDao setGroupDao;
    private final WorkoutDao workoutDao;
    private final SessionDao sessionDao;
    private final ExerciseDao exerciseDao;
    private final AuthService authService;

    @Autowired
    public SetGroupDtoValidator(SetGroupDao setGroupDao, WorkoutDao workoutDao, SessionDao sessionDao,
                                ExerciseDao exerciseDao, AuthService authService) {
        this.setGroupDao = setGroupDao;
        this.workoutDao = workoutDao;
        this.sessionDao = sessionDao;
        this.exerciseDao = exerciseDao;
        this.authService = authService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SetGroupDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final SetGroupDto setGroupDto = (SetGroupDto) target;
        final boolean exists = setGroupDto.getId() != null && setGroupDto.getId() > 0L;

        this.validateDescription(setGroupDto, errors);
        this.validateExercise(setGroupDto, errors);

        if (exists) {
            this.validateAccess(setGroupDto, errors);
        } else {
            this.validateParent(setGroupDto, errors);
        }
    }

    private void validateDescription(final SetGroupDto setGroupDto, final Errors errors) {
        final String fieldName = "description";
        final String description = setGroupDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, ERR300.name(), ERR300.defaultMessage);
        }
    }

    private void validateExercise(final SetGroupDto setGroupDto, final Errors errors) {
        final String fieldName = "exercise";
        final ExerciseDto exerciseDto = setGroupDto.getExercise();
        if (exerciseDto == null || exerciseDto.getId() == null || exerciseDto.getId() <= 0L || !this.exerciseDao.existsById(exerciseDto.getId())) {
            errors.rejectValue(fieldName, ERR509.name(), ERR509.defaultMessage);
            return;
        }

        try {
            this.authService.checkAccess(this.exerciseDao.getReferenceById(exerciseDto.getId()));
        } catch (IllegalAccessException e) {
            errors.rejectValue(fieldName, ERR510.name(), ERR510.defaultMessage);
        }
    }

    private void validateAccess(final SetGroupDto setGroupDto, final Errors errors) {
        final String fieldName = "id";
        final Long setGroupId = setGroupDto.getId();
        if (!this.setGroupDao.existsById(setGroupId)) {
            errors.rejectValue(fieldName, ERR309.name(), ERR309.defaultMessage);
            return;
        }

        try {
            this.authService.checkAccess(this.setGroupDao.getReferenceById(setGroupId));
        } catch (IllegalAccessException e) {
            errors.rejectValue(fieldName, ERR310.name(), ERR310.defaultMessage);
        }
    }

    private void validateParent(final SetGroupDto setGroupDto, final Errors errors) {
        final String fieldName = "session-workout";
        final SessionDto sessionDto = setGroupDto.getSession();
        final WorkoutDto workoutDto = setGroupDto.getWorkout();
        final boolean existsSession = sessionDto != null && sessionDto.getId() != null && sessionDto.getId() > 0L;
        final boolean existsWorkout = workoutDto != null && workoutDto.getId() != null && workoutDto.getId() > 0L;

        if (existsSession && existsWorkout) {
            errors.rejectValue(fieldName, ERR400.name(), ERR400.defaultMessage);
            return;
        } else if (!existsSession && !existsWorkout) {
            errors.rejectValue(fieldName, ERR401.name(), ERR401.defaultMessage);
            return;
        }

        if (existsSession) {
            this.validateSession(sessionDto, errors);
        } else {
            this.validateWorkout(workoutDto, errors);
        }
    }

    private void validateSession(final SessionDto sessionDto, final Errors errors) {
        final String fieldName = "session";
        if (!this.sessionDao.existsById(sessionDto.getId())) {
            errors.rejectValue(fieldName, ERR609.name(), ERR609.defaultMessage);
            return;
        }

        try {
            this.authService.checkAccess(this.sessionDao.getReferenceById(sessionDto.getId()).getProgram());
        } catch (IllegalAccessException e) {
            errors.rejectValue(fieldName, ERR610.name(), ERR610.defaultMessage);
        }
    }

    private void validateWorkout(final WorkoutDto workoutDto, final Errors errors) {
        final String fieldName = "workout";
        if (!this.workoutDao.existsById(workoutDto.getId())) {
            errors.rejectValue(fieldName, ERR709.name(), ERR709.defaultMessage);
            return;
        }

        try {
            this.authService.checkAccess(this.workoutDao.getReferenceById(workoutDto.getId()));
        } catch (IllegalAccessException e) {
            errors.rejectValue(fieldName, ERR710.name(), ERR710.defaultMessage);
        }
    }

}
