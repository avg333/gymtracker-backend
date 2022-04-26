package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.config.security.MyUserDetails;
import org.avillar.gymtracker.model.dao.UserRepository;
import org.avillar.gymtracker.model.entities.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final UserApp userApp = userRepository.findByUsername(username);
        if (userApp == null) {
            throw new UsernameNotFoundException(username);
        }
        return new MyUserDetails(userApp, Collections.emptyList());
    }
}
