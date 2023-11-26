package org.avillar.gymtracker.usersapi.modifysettings.infrastructure.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.usersapi.common.properties.UserProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@RequiredArgsConstructor
@Component
public class SelectedPlatesValidatorImpl
    implements ConstraintValidator<SelectedPlatesValidator, List<Double>> {

  private final UserProperties userProperties;

  private static boolean hasDuplicates(List<Double> value) {
    return value.size() != new HashSet<>(value).size();
  }

  @Override
  public boolean isValid(final List<Double> value, final ConstraintValidatorContext context) {
    if (CollectionUtils.isEmpty(value) || hasDuplicates(value)) {
      return false;
    }

    final List<Double> validPlates = userProperties.getValidPlates();
    if (CollectionUtils.isEmpty(validPlates)) {
      return true;
    }

    return value.stream().noneMatch(validPlates::contains);
  }
}
