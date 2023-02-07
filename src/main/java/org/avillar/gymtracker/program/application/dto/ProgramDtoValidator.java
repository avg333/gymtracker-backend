package org.avillar.gymtracker.program.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.session.application.dto.SessionDto;
import org.avillar.gymtracker.set.application.dto.SetDto;
import org.springframework.lang.NonNull;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class ProgramDtoValidator implements Validator {
    private static final int LONG_MAX_DESC = 255;
    private static final int MAX_REPS = 50;
    private static final double MIN_RIR = 0;
    private static final double MAX_RIR = 5;
    private static final double MAX_WEIGHT = 2000;


    @Override
    public boolean supports(@NonNull Class<?> clazz) {
        return ProgramDto.class.equals(clazz);
    }

    @Override
    public void validate(@NonNull Object target, @NonNull Errors errors) {
        final ProgramDto programDto = (ProgramDto) target;

        this.validateDescription(programDto, errors);
    }


    private void validateName(final SessionDto sessionDto, final Errors errors) {
        final String fieldName = "description";
        final String description = sessionDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, "field.description.max", "La longitud de la descripcion de la set es superior a la maxima");
        }
    }

    private void validateDescription(final ProgramDto programDto, final Errors errors) {
        final String fieldName = "description";
        final String description = programDto.getDescription();
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
