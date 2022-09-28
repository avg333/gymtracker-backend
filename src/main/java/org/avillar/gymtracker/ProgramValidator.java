package org.avillar.gymtracker;

import org.avillar.gymtracker.model.dto.ProgramDto;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProgramValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {

    }

    public Errors validaNuevoPrograma(final ProgramDto programDto) {
        final Errors errors = new BeanPropertyBindingResult(programDto, "programDto");

        if (programDto.getName() != null) {
            errors.reject("error");
        }
        return errors;
    }
}
