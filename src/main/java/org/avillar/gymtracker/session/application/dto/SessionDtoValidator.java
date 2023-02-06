package org.avillar.gymtracker.session.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.set.application.dto.SetDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class SessionDtoValidator implements Validator {
    private static final int LONG_MAX_DESC = 255;
    private static final int MAX_REPS = 50;
    private static final double MIN_RIR = 0;
    private static final double MAX_RIR = 5;
    private static final double MAX_WEIGHT = 2000;


    @Override
    public boolean supports(Class<?> clazz) {
        return SessionDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final SessionDto sessionDto = (SessionDto) target;

        this.validateListOrder(sessionDto, errors);
        this.validateDescription(sessionDto, errors);
    }


    private void validateListOrder(final SessionDto sessionDto, final Errors errors) {
        final String fieldName = "listOrder";
        final int listOrder = sessionDto.getListOrder();
        final Long setId = sessionDto.getId();
        //TODO
    }

    private void validateName(final SessionDto sessionDto, final Errors errors) {
        final String fieldName = "description";
        final String description = sessionDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, "field.description.max", "La longitud de la descripcion de la set es superior a la maxima");
        }
    }

    private void validateDescription(final SessionDto sessionDto, final Errors errors) {
        final String fieldName = "description";
        final String description = sessionDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, "field.description.max", "La longitud de la descripcion de la set es superior a la maxima");
        }
    }

    private void validateDayOfWeek(final SetDto setDto, final Errors errors) {
        final String fieldName = "description";
        final String description = setDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, "field.description.max", "La longitud de la descripcion de la set es superior a la maxima");
        }
    }

    private void validateProgram(final SetDto setDto, final Errors errors) {
        final String fieldName = "description";
        final String description = setDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, "field.description.max", "La longitud de la descripcion de la set es superior a la maxima");
        }
    }
}
