package org.avillar.gymtracker.workout.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.user.application.UserAppDto;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class WorkoutValidator {
    private static final int LONG_MAX_DESC = 255;

    private final UserDao userDao;

    public WorkoutValidator(UserDao userDao) {
        this.userDao = userDao;
    }

    private static void validateDescription(final WorkoutDto workoutDto, final Map<String, String> errorMap) {
        final String fieldName = "description";
        final String description = workoutDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errorMap.put(fieldName, "La longitud de la descripcion del workout es superior a la maxima");
        }
    }

    private static void validateDate(final WorkoutDto workoutDto, final Map<String, String> errorMap) {
        final String fieldName = "date";
        final Date date = workoutDto.getDate();
        if (date == null) {
            errorMap.put(fieldName, "La fecha del workout no es valida");
        } //TODO Añadir fecha maxima y minima
    }

    public Map<String, String> validate(final WorkoutDto workoutDto, final Map<String, String> errorMap) {
        validateDate(workoutDto, errorMap);
        validateDescription(workoutDto, errorMap);
        if (workoutDto.getId() == null) {
            validateUser(workoutDto, errorMap);
        }
        return errorMap;
    }

    private void validateUser(final WorkoutDto workoutDto, final Map<String, String> errorMap) {
        final String fieldName = "user";
        final UserAppDto userAppDto = workoutDto.getUserApp();
        if (userAppDto == null || userAppDto.getId() == null || !this.userDao.existsById(userAppDto.getId())) {
            errorMap.put(fieldName, "El usuario no existe");
            throw new EntityNotFoundException(UserApp.class, userAppDto != null ? userAppDto.getId() : 0L);
        }
    }
}
