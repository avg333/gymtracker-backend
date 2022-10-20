package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.UserRepository;
import org.avillar.gymtracker.model.dto.UserAppDto;
import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<UserAppDto> getAllUsers() {
        return this.userRepository.findAll().stream().map(userApp -> this.modelMapper.map(userApp, UserAppDto.class)).toList();
    }

    @Override
    public UserAppDto getUser(final Long userId) {
        return this.modelMapper.map(this.userRepository.getById(userId), UserAppDto.class);
    }

    @Override
    public UserAppDto createUser(final UserAppDto userAppDto) {
        userAppDto.setId(null);
        userAppDto.setPassword(bCryptPasswordEncoder.encode(userAppDto.getPassword()));
        final UserApp userApp = this.modelMapper.map(userAppDto, UserApp.class);
        return this.modelMapper.map(this.userRepository.save(userApp), UserAppDto.class);
    }

    @Override
    public UserAppDto updateUser(final UserAppDto userAppDto) {
        userAppDto.setPassword(bCryptPasswordEncoder.encode(userAppDto.getPassword()));
        final UserApp userApp = this.modelMapper.map(userAppDto, UserApp.class);
        return this.modelMapper.map(this.userRepository.save(userApp), UserAppDto.class);
    }

    @Override
    public void deleteUser(final Long userId) {
        this.userRepository.deleteById(userId);
    }
}
