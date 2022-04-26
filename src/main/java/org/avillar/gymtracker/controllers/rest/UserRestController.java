package org.avillar.gymtracker.controllers.rest;

import org.avillar.gymtracker.model.dao.UserRepository;
import org.avillar.gymtracker.model.entities.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserRestController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserRestController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("/users/")
    public List<UserApp> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{userId}")
    public UserApp getUsuario(@PathVariable final Long userId) {
        return userRepository.getById(userId);
    }

    @PostMapping("/users/")
    public UserApp addUser(@RequestBody final UserApp userApp) {
        userApp.setPassword(bCryptPasswordEncoder.encode(userApp.getPassword()));
        return userRepository.save(userApp);
    }
}
