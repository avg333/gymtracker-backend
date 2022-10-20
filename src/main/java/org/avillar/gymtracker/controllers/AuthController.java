package org.avillar.gymtracker.controllers;

import org.avillar.gymtracker.config.security.MyUserDetails;
import org.avillar.gymtracker.config.security.jwt.JwtUtils;
import org.avillar.gymtracker.model.dto.UserAppDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<UserAppDto> login(@RequestBody final UserAppDto userAppDto) {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userAppDto.getUsername(), userAppDto.getPassword());
        final Authentication authentication = this.authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new UserAppDto(userDetails.getUserApp().getId(), this.jwtUtils.generateJwtToken(authentication), userDetails.getUsername(), null, null));
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> logout() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok().build();
    }
}
