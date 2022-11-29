package org.avillar.gymtracker.user.application;

import org.avillar.gymtracker.security.MyUserDetails;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserApp userApp = userDao.findByUsername(username);
        if (null == userApp) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserDetails(userApp, Collections.emptyList());
    }
}
