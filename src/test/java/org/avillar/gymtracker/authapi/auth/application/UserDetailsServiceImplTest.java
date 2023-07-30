package org.avillar.gymtracker.authapi.auth.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.avillar.gymtracker.authapi.domain.UserApp;
import org.avillar.gymtracker.authapi.domain.UserDao;
import org.jeasy.random.EasyRandom;
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

  private final EasyRandom easyRandom = new EasyRandom();

  @InjectMocks private UserDetailsServiceImpl userDetailsService;

  @Mock private UserDao userDao;

  @Test
  void loadUserByUsernameOk() {
    final String username = easyRandom.nextObject(String.class);
    final UserApp expected = easyRandom.nextObject(UserApp.class);

    when(userDao.findByUsername(username)).thenReturn(expected);

    final UserDetails result = userDetailsService.loadUserByUsername(username);
    assertEquals(expected.getUsername(), result.getUsername());
    assertEquals(expected.getPassword(), result.getPassword());
    assertEquals(1, result.getAuthorities().size());
  }

  @Test
  void loadUserByUsernameKo() {
    final String username = easyRandom.nextObject(String.class);

    when(userDao.findByUsername(username)).thenReturn(null);

    assertThrows(
        UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
  }
}
