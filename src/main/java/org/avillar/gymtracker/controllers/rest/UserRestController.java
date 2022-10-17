package org.avillar.gymtracker.controllers.rest;

import org.avillar.gymtracker.model.dao.UserRepository;
import org.avillar.gymtracker.model.entities.UserApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserRestController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping("")
    public List<UserApp> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{userId}")
    public UserApp getUsuario(@PathVariable final Long userId) {
        return userRepository.getById(userId);
    }

    @PostMapping("")
    public ResponseEntity<UserApp> addUser(@RequestBody final UserApp userApp) {
        userApp.setPassword(bCryptPasswordEncoder.encode(userApp.getPassword()));
        return ResponseEntity.ok(userRepository.save(userApp));
    }
}
