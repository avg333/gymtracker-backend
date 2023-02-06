package org.avillar.gymtracker.set.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


public class SetDtoValidator implements Validator {
    private static final int LONG_MAX_DESC = 255;
    private static final int MAX_REPS = 50;
    private static final double MIN_RIR = 0;
    private static final double MAX_RIR = 5;
    private static final double MAX_WEIGHT = 2000;


    @Override
    public boolean supports(Class<?> clazz) {
        return SetDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final SetDto setDto = (SetDto) target;

        this.validateListOrder(setDto, errors);
        this.validateDescription(setDto, errors);
        this.validateReps(setDto, errors);
        this.validateRir(setDto, errors);
        this.validateWeight(setDto, errors);
        this.validateSetGroup(setDto, errors);
    }


    private void validateListOrder(final SetDto setDto, final Errors errors) {
        final String fieldName = "listOrder";
        final int listOrder = setDto.getListOrder();
        final Long setId = setDto.getId();
        //TODO
    }

    private void validateDescription(final SetDto setDto, final Errors errors) {
        final String fieldName = "description";
        final String description = setDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, "field.description.max", "La longitud de la descripcion de la set es superior a la maxima");
        }
    }

    private void validateReps(final SetDto setDto, final Errors errors) {
        final String fieldName = "reps";
        final int reps = setDto.getReps();
        if (reps < 0) {
            errors.rejectValue(fieldName, "field.reps.negative", "Las repeticiones no pueden ser negativas");
        }
        if (reps > MAX_REPS) {
            errors.rejectValue(fieldName, "field.reps.max", "El número de reps excede el maximo");
        }
    }

    private void validateRir(final SetDto setDto, final Errors errors) {
        final String fieldName = "rir";
        final double rir = setDto.getRir();
        if (rir < MIN_RIR) {
            errors.rejectValue(fieldName, "field.rir.min", "El RIR es inferior al mínimo");
        }
        if (rir > MAX_RIR) {
            errors.rejectValue(fieldName, "field.rir.max", "El RIR es superior al maximo");
        }
        //TODO Verificar si es +-0,5
    }

    private void validateWeight(final SetDto setDto, final Errors errors) {
        final String fieldName = "weight";
        final double weight = setDto.getWeight();
        if (weight < 0) {
            errors.rejectValue(fieldName, "field.weight.negative", "El peso no puede ser inferior al minimo");
        }
        if (weight > MAX_WEIGHT) {
            errors.rejectValue(fieldName, "field.weight.max", "El peso no puede ser superior al maximo");
        }
    }

    private void validateSetGroup(final SetDto setDto, final Errors errors) {
        final String fieldName = "setGroup";//TODO
        final int reps = setDto.getReps();
        if (reps < 0) {
            errors.rejectValue(fieldName, "field.setGroup.notExists", "Las repeticiones no pueden ser negativas");
        }
        if (reps > MAX_REPS) {
            errors.rejectValue(fieldName, "field.setGroup.notPermission", "El numero de reps excede el maximo");
        }
    }
}
