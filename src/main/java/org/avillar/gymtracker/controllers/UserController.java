package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.model.dto.UserAppDto;
import org.avillar.gymtracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<UserAppDto>> getAllUsers() {
        return ResponseEntity.ok(this.userService.getAllUsers());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserAppDto> getUser(@PathVariable final Long userId) {
        return ResponseEntity.ok(this.userService.getUser(userId));
    }

    @PostMapping("")
    public ResponseEntity<UserAppDto> postUser(@RequestBody final UserAppDto userAppDto) {
        userAppDto.setId(null);

        return ResponseEntity.ok(this.userService.createUser(userAppDto));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserAppDto> putUser(@PathVariable final Long userId, @RequestBody final UserAppDto userAppDto) {
        userAppDto.setId(userId);

        return ResponseEntity.ok(this.userService.updateUser(userAppDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
