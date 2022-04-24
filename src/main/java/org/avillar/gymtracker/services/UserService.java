package org.avillar.gymtracker.services;

import org.avillar.gymtracker.model.entities.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUser(Long userId);

    User getUserByUsername(String username);

    User createUser(User user);

    User updateUser(User user);

    void deleteUser(Long userId);
}
