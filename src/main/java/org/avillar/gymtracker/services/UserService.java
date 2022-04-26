package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.entities.UserApp;

import java.util.List;

public interface UserService {
    List<UserApp> getAllUsers();

    UserApp getUser(Long userId);

    UserApp getUserByUsername(String username);

    UserApp createUser(UserApp userApp);

    UserApp updateUser(UserApp userApp);

    void deleteUser(Long userId);
}
