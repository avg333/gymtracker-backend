package org.avillar.gymtracker.services.impl;

import org.avillar.gymtracker.model.dao.UserRepository;
import org.avillar.gymtracker.model.entities.User;
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
    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    @Override
    public User getUser(final Long userId) {
        return this.userRepository.getById(userId);
    }

    @Override
    public User getUserByUsername(final String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User createUser(final User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(final User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(final Long userId) {
        this.userRepository.deleteById(userId);
    }
}
