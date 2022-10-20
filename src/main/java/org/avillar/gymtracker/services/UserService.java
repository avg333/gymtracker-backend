package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.dto.UserAppDto;

import java.util.List;

public interface UserService {
    List<UserAppDto> getAllUsers();

    UserAppDto getUser(Long userId);

    UserAppDto createUser(UserAppDto userAppDto);

    UserAppDto updateUser(UserAppDto userAppDto);

    void deleteUser(Long userId);
}
