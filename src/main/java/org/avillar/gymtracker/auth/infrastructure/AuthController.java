package org.avillar.gymtracker.auth.infrastructure;

import jakarta.persistence.EntityNotFoundException;
import org.avillar.gymtracker.auth.application.AuthService;
import org.avillar.gymtracker.user.application.UserAppDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<UserAppDto> login(@RequestBody final UserAppDto userAppDto) {
        try {
            return ResponseEntity.ok(this.authService.login(userAppDto));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/signout")
    public ResponseEntity<Void> logout() {
        this.authService.logout();
        return ResponseEntity.ok().build();
    }
}
