package com.syntiq.handy.controller;

import com.syntiq.handy.model.dto.UserInfoDto;
import com.syntiq.handy.model.entity.User;
import com.syntiq.handy.repository.UserRepository;
import com.syntiq.handy.service.UserRegistrationService;
import com.syntiq.handy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/user")
public class UserRegistrationController {
    @Autowired
    private UserService userService;
    @Autowired private UserRepository repository;
    @Autowired
    private UserRegistrationService userRegistrationService;
    @GetMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserInfoDto> register(Authentication authentication) {
        Jwt jwt = (Jwt)authentication.getPrincipal();
        UserInfoDto dto = userRegistrationService.registerUser(jwt.getTokenValue());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UserInfoDto> createUser(@RequestBody UserInfoDto dto) {
        UserInfoDto created = userService.createUser(dto);
        System.out.println(created.getId());
        return ResponseEntity.status(201).body(created);
    }

    @GetMapping
    public ResponseEntity<List<User>> test() {
        return ResponseEntity.ok(this.repository.findAll());
    }
}
