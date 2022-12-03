package org.avillar.gymtracker.user.application;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.user.domain.UserApp;
import org.avillar.gymtracker.user.domain.UserDao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final String USER_NOT_FOUND = "The user does not exist";

    private final UserDao userDao;
    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<UserAppDto> getAllUsers() {
        return this.userDao.findAll().stream().map(userApp -> this.modelMapper.map(userApp, UserAppDto.class)).toList();
    }

    @Override
    public UserAppDto getUser(final Long userId) {
        return this.modelMapper.map(this.userDao.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(USER_NOT_FOUND)), UserAppDto.class);
    }

    @Override
    public UserAppDto createUser(final UserAppDto userAppDto) {
        userAppDto.setId(null);
        userAppDto.setPassword(bCryptPasswordEncoder.encode(userAppDto.getPassword()));
        final UserApp userApp = this.modelMapper.map(userAppDto, UserApp.class);
        return this.modelMapper.map(this.userDao.save(userApp), UserAppDto.class);
    }

    @Override
    public UserAppDto updateUser(final UserAppDto userAppDto) {
        userAppDto.setPassword(bCryptPasswordEncoder.encode(userAppDto.getPassword()));
        final UserApp userApp = this.modelMapper.map(userAppDto, UserApp.class);
        return this.modelMapper.map(this.userDao.save(userApp), UserAppDto.class);
    }

    @Override
    public void deleteUser(final Long userId) {
        this.userDao.deleteById(userId);
    }

}
