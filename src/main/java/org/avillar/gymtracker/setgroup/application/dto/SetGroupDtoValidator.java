package org.avillar.gymtracker.setgroup.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.exercise.application.dto.ExerciseDto;
import org.avillar.gymtracker.session.application.dto.SessionDto;
import org.avillar.gymtracker.workout.application.dto.WorkoutDto;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SetGroupDtoValidator implements Validator {
    private static final int LONG_MAX_DESC = 255;

    @Override
    public boolean supports(Class<?> clazz) {
        return SetGroupDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final SetGroupDto setGroupDto = (SetGroupDto) target;

        this.validateListOrder(setGroupDto, errors);
        this.validateDescription(setGroupDto, errors);
        this.validateExercise(setGroupDto, errors);
        this.validateParent(setGroupDto, errors);
    }

    private void validateListOrder(final SetGroupDto setGroupDto, final Errors errors) {
        final String fieldName = "listOrder";
        final int listOrder = setGroupDto.getListOrder();
        final Long setId = setGroupDto.getId();
        //TODO
    }

    private void validateDescription(final SetGroupDto setGroupDto, final Errors errors) {
        final String fieldName = "description";
        final String description = setGroupDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, "field.description.max", "La longitud de la descripcion de la set es superior a la maxima");
        }
    }

    private void validateExercise(final SetGroupDto setGroupDto, final Errors errors) {
        final String fieldName = "exercise";
        final ExerciseDto exerciseDto = setGroupDto.getExercise();
        if (exerciseDto == null || exerciseDto.getId() == null || exerciseDto.getId() <= 0L) {
            errors.rejectValue(fieldName, "field.exercise.notExists", "El ejercicio especificado no existe");
        }
        //TODO Validar acceso
    }

    private void validateParent(final SetGroupDto setGroupDto, final Errors errors) {
        final String fieldName = "exercise";
        final SessionDto sessionDto = setGroupDto.getSession();
        final WorkoutDto workoutDto = setGroupDto.getWorkout();
        final boolean existsSession = sessionDto == null || sessionDto.getId() == null || sessionDto.getId() <= 0L;
        final boolean existsWorkout = workoutDto == null || workoutDto.getId() == null || workoutDto.getId() <= 0L;
        if (!existsSession && !existsWorkout) {
            errors.rejectValue(fieldName, "field.parent.notExists", "No existe ninguna session ni workout especificada");
        }
        //TODO Validar acceso
    }

}
