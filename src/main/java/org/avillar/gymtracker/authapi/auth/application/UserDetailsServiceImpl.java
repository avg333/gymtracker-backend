package org.avillar.gymtracker.authapi.auth.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.avillar.gymtracker.authapi.auth.domain.UserApp;
import org.avillar.gymtracker.authapi.auth.domain.UserDao;
import org.avillar.gymtracker.common.auth.jwt.UserDetailsImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserDao userDao;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    final UserApp userApp = userDao.findByUsername(username);
    if (null == userApp) {
      throw new UsernameNotFoundException(username);
    }

    return new UserDetailsImpl(
        userApp.getId(),
        userApp.getUsername(),
        userApp.getPassword(),
        List.of(new SimpleGrantedAuthority("USER_ROLE")));
  }
}
