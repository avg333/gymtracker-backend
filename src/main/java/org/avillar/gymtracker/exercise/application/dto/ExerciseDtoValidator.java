package org.avillar.gymtracker.exercise.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.enums.domain.LoadTypeEnum;
import org.avillar.gymtracker.set.application.dto.SetDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.Map;

public class ExerciseDtoValidator implements Validator {

    private static final int LONG_MIN_NAME = 10;
    private static final int LONG_MAX_NAME = 100;
    private static final int LONG_MAX_DESC = 255;
    private static final int LONG_MIN_WEIGHT = 0;
    private static final int LONG_MAX_WEIGHT = 1;

    private static void validateLoadType(final ExerciseDto exerciseDto, final Map<String, String> errorMap) {
        final String fieldName = "loadType";
        final LoadTypeEnum loadTypeEnum = exerciseDto.getLoadType();
        if (loadTypeEnum == null || !Arrays.asList(LoadTypeEnum.values()).contains(loadTypeEnum)) {
            errorMap.put(fieldName, "LoadType debe de ser un valor del enumerado");
        }
    }

    private static void validateUnilateral(final ExerciseDto exerciseDto, final Map<String, String> errorMap) {
        final String fieldName = "unilateral";
        final Boolean unilateral = exerciseDto.getUnilateral();
        if (unilateral == null) {
            errorMap.put(fieldName, "Unilateral debe tener un valor");
        }
    }

    private static void validateDescription(final ExerciseDto exerciseDto, final Map<String, String> errorMap) {
        final String fieldName = "description";
        final String description = exerciseDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errorMap.put(fieldName, "La longitud de la descripcion del ejercicio es superior a la maxima");
        }
    }

    private static void validateName(final ExerciseDto exerciseDto, final Map<String, String> errorMap) {
        final String fieldName = "name";
        final String name = exerciseDto.getName();
        if (StringUtils.isBlank(name)) {
            errorMap.put(fieldName, "El nombre del ejercicio esta en blanco");
        } else if (name.length() < LONG_MIN_NAME) {
            errorMap.put(fieldName, "La longitud del nombre del ejercicio es inferior a la minima");
        } else if (name.length() > LONG_MAX_NAME) {
            errorMap.put(fieldName, "La longitud del nombre del ejercicio es superior a la maxima");
        }
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SetDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final ExerciseDto exerciseDto = (ExerciseDto) target;

        //TODO
    }

}
