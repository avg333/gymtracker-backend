package org.avillar.gymtracker.set.application.dto;

import org.apache.commons.lang3.StringUtils;
import org.avillar.gymtracker.auth.application.AuthService;
import org.avillar.gymtracker.errors.application.exceptions.IllegalAccessException;
import org.avillar.gymtracker.set.domain.SetDao;
import org.avillar.gymtracker.setgroup.application.dto.SetGroupDto;
import org.avillar.gymtracker.setgroup.domain.SetGroupDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.avillar.gymtracker.errors.application.ErrorCodes.*;

@Component
public class SetDtoValidator implements Validator {
    private static final int LONG_MAX_DESC = 255;
    private static final int MAX_REPS = 50;
    private static final double MIN_RIR = 0;
    private static final double MAX_RIR = 5;
    private static final double MAX_WEIGHT = 2000;

    private final SetDao setDao;
    private final SetGroupDao setGroupDao;
    private final AuthService authService;

    @Autowired
    public SetDtoValidator(SetDao setDao, SetGroupDao setGroupDao, AuthService authService) {
        this.setDao = setDao;
        this.setGroupDao = setGroupDao;
        this.authService = authService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SetDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        final SetDto setDto = (SetDto) target;
        final boolean exists = setDto.getId() != null && setDto.getId() > 0L;

        this.validateDescription(setDto, errors);
        this.validateReps(setDto, errors);
        this.validateRir(setDto, errors);
        this.validateWeight(setDto, errors);

        if (exists) {
            this.validateAccess(setDto, errors);
        } else {
            this.validateSetGroup(setDto, errors);
        }
    }

    private void validateDescription(final SetDto setDto, final Errors errors) {
        final String fieldName = "description";
        final String description = setDto.getDescription();
        if (StringUtils.isNotEmpty(description) && description.length() > LONG_MAX_DESC) {
            errors.rejectValue(fieldName, ERR_BF_CMM_20.name(), ERR_BF_CMM_20.defaultMessage);
        }
    }

    private void validateReps(final SetDto setDto, final Errors errors) {
        final String fieldName = "reps";
        final int reps = setDto.getReps();
        if (reps < 0) {
            errors.rejectValue(fieldName, ERR_BF_SET_20.name(), ERR_BF_SET_20.defaultMessage);
        } else if (reps > MAX_REPS) {
            errors.rejectValue(fieldName, ERR_BF_SET_21.name(), ERR_BF_SET_21.defaultMessage);
        }
    }

    private void validateRir(final SetDto setDto, final Errors errors) {
        final String fieldName = "rir";
        final double rir = setDto.getRir();
        if (rir < MIN_RIR) {
            errors.rejectValue(fieldName, ERR_BF_SET_22.name(), ERR_BF_SET_22.defaultMessage);
        } else if (rir > MAX_RIR) {
            errors.rejectValue(fieldName, ERR_BF_SET_23.name(), ERR_BF_SET_23.defaultMessage);
        }
        //TODO Verificar si es +-0,5
    }

    private void validateWeight(final SetDto setDto, final Errors errors) {
        final String fieldName = "weight";
        final double weight = setDto.getWeight();
        if (weight < 0) {
            errors.rejectValue(fieldName, ERR_BF_SET_24.name(), ERR_BF_SET_24.defaultMessage);
        } else if (weight > MAX_WEIGHT) {
            errors.rejectValue(fieldName, ERR_BF_SET_25.name(), ERR_BF_SET_25.defaultMessage);
        }
    }

    private void validateAccess(final SetDto setDto, final Errors errors) {
        final String fieldName = "id";
        final Long setId = setDto.getId();
        if (!this.setDao.existsById(setId)) {
            errors.rejectValue(fieldName, ERR_BF_SET_04.name(), ERR_BF_SET_04.defaultMessage);
            return;
        }

        try {
            this.authService.checkAccess(this.setDao.getReferenceById(setId).getSetGroup().getWorkout());
        } catch (IllegalAccessException e) {
            errors.rejectValue(fieldName, ERR_BF_SET_03.name(), ERR_BF_SET_03.defaultMessage);
        }
    }

    private void validateSetGroup(final SetDto setDto, final Errors errors) {
        final String fieldName = "setGroup";
        final SetGroupDto setGroupDto = setDto.getSetGroup();
        if (setGroupDto == null || setGroupDto.getId() == null || setGroupDto.getId() <= 0L || !this.setGroupDao.existsById(setGroupDto.getId())) {
            errors.rejectValue(fieldName, ERR_BF_SETGROUP_04.name(), ERR_BF_SETGROUP_04.defaultMessage);
            return;
        }

        try {
            this.authService.checkAccess(this.setGroupDao.getReferenceById(setGroupDto.getId()).getWorkout());
        } catch (IllegalAccessException e) {
            errors.rejectValue(fieldName, ERR_BF_SETGROUP_03.name(), ERR_BF_SETGROUP_03.defaultMessage);
        }
    }

}
