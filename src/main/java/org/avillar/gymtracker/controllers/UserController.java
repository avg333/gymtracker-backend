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
    public ResponseEntity<UserAppDto> addUser(@RequestBody final UserAppDto userAppDto) {
        return ResponseEntity.ok(this.userService.createUser(userAppDto));
    }

    @PostMapping("/{userId}")
    public ResponseEntity<UserAppDto> updateUser(@PathVariable final Long userId, @RequestBody final UserAppDto userAppDto) {
        if (!userId.equals(userAppDto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(this.userService.updateUser(userAppDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable final Long userId) {
        this.userService.deleteUser(userId);
        return ResponseEntity.ok().build();
    }
}
