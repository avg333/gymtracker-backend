package org.avillar.gymtracker.authapi.auth.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.common.exception.application.UserNotFoundException;
import org.avillar.gymtracker.authapi.common.facade.user.UserFacade;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Execution(ExecutionMode.CONCURRENT)
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

  @InjectMocks private UserDetailsServiceImpl userDetailsService;

  @Mock private UserFacade userFacade;

  @Test
  void shouldLoadUserDetailsByUsernameSuccessfully() throws UserNotFoundException {
    final String username = Instancio.create(String.class);
    final UserApp expected = Instancio.create(UserApp.class);

    when(userFacade.findByUsername(username)).thenReturn(expected);

    final UserDetails result = userDetailsService.loadUserByUsername(username);
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo(expected.getUsername());
    assertThat(result.getPassword()).isEqualTo(expected.getPassword());
    assertThat(result.getAuthorities()).isNotNull().hasSize(1);
  }

  @Test
  void shouldThrowUsernameNotFoundExceptionWhenUsernameIsNotFound() throws UserNotFoundException {
    final String username = Instancio.create(String.class);

    doThrow(UserNotFoundException.class).when(userFacade).findByUsername(username);

    assertThrows(
        UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
  }
}
