package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.UserRepository;
import org.avillar.gymtracker.model.entities.UserApp;
import org.avillar.gymtracker.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<UserApp> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public UserApp getUser(final Long userId) {
        return this.userRepository.getById(userId);
    }

    @Override
    public UserApp getUserByUsername(final String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public UserApp createUser(final UserApp userApp) {
        return this.userRepository.save(userApp);
    }

    @Override
    public UserApp updateUser(final UserApp userApp) {
        return this.userRepository.save(userApp);
    }

    @Override
    public void deleteUser(final Long userId) {
        this.userRepository.deleteById(userId);
    }
}
