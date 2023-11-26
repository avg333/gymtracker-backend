package org.avillar.gymtracker.usersapi.modifysettings.infrastructure.validator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.avillar.gymtracker.usersapi.common.properties.UserProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SelectedPlatesValidatorImplTest {

  private static final List<Double> VALID_PLATES = List.of(1.25, 2.5, 5.0, 10.0);

  @InjectMocks private SelectedPlatesValidatorImpl selectedPlatesValidatorImpl;

  @Mock private UserProperties userProperties;

  @Test
  void shouldReturnFalseWhenSelectedPlatesAreEmpty() {
    assertThat(selectedPlatesValidatorImpl.isValid(Collections.emptyList(), null)).isFalse();
  }

  @Test
  void shouldReturnFalseWhenSelectedPlatesAreNull() {
    assertThat(selectedPlatesValidatorImpl.isValid(null, null)).isFalse();
  }

  @Test
  void shouldReturnFalseWhenSelectedPlatesHaveRepeatedValues() {
    List<Double> request = List.of(VALID_PLATES.get(0), VALID_PLATES.get(0));

    assertThat(selectedPlatesValidatorImpl.isValid(request, null)).isFalse();
  }

  @Test
  void shouldReturnFalseWhenSelectedPlatesContainsNotValidPlates() {
    List<Double> request = List.of(VALID_PLATES.get(0), VALID_PLATES.get(1), 2.3);

    when(userProperties.getValidPlates()).thenReturn(VALID_PLATES);

    assertThat(selectedPlatesValidatorImpl.isValid(request, null)).isFalse();
  }

  @Test
  void shouldReturnTrueWhenValidPlatesIsEmpty() {
    List<Double> request = List.of(VALID_PLATES.get(0), VALID_PLATES.get(1), 2.3);

    when(userProperties.getValidPlates()).thenReturn(Collections.emptyList());

    assertThat(selectedPlatesValidatorImpl.isValid(request, null)).isTrue();
  }
}
