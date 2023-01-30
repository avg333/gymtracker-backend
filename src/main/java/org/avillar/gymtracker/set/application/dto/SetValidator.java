package org.avillar.gymtracker.set.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.errors.application.EntityNotFoundException;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.avillar.gymtracker.setgroup.domain.SetGroup;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class SetValidator {
    private static final int LONG_MAX_DESC = 255;

    private final SetGroupDao setGroupDao;

    public SetValidator(SetGroupDao setGroupDao) {
        this.setGroupDao = setGroupDao;
    }

    private static void validateDescription(final SetDto setDto, final Map<String, String> errorMap) {
        final String fieldName = "description";
        final String description = setDto.getDescription();
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

    //TODO por acabar
    public Map<String, String> validate(final SetDto setDto, final Map<String, String> errorMap) {

        validateDescription(setDto, errorMap);

        this.validateSetGroup(setDto, errorMap);
        return errorMap;
    }

    private void validateSetGroup(final SetDto setDto, final Map<String, String> errorMap) {
        final String fieldName = "description";
        final SetGroupDto setGroupDto = setDto.getSetGroup();
        if (setGroupDto == null || setGroupDto.getId() == null || !this.setGroupDao.existsById(setGroupDto.getId())) {
            errorMap.put(fieldName, "El SetGroup no existe");
            throw new EntityNotFoundException(SetGroup.class, setGroupDto != null ? setGroupDto.getId() : 0L);
        }

    }

}
