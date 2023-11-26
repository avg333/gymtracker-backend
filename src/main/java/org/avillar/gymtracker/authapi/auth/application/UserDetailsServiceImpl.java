package org.avillar.gymtracker.authapi.auth.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.authapi.common.domain.UserApp;
import org.avillar.gymtracker.authapi.common.exception.application.UserNotFoundException;
import org.avillar.gymtracker.authapi.common.facade.user.UserFacade;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private static final String DEFAULT_ROLE = "USER_ROLE";

  private final UserFacade userFacade;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    try {
      final UserApp userApp = userFacade.findByUsername(username);

      return new UserDetailsImpl(
          userApp.getId(),
          userApp.getUsername(),
          userApp.getPassword(),
          List.of(new SimpleGrantedAuthority(DEFAULT_ROLE)));

    } catch (UserNotFoundException e) {
      throw new UsernameNotFoundException(username);
    }
  }
}
