package com.creve.controller;

import com.creve.config.JwtUtils;
import com.creve.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserService userService;
    @Autowired private JwtUtils jwtUtils;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        userService.registerUser(request.get("name"), request.get("email"), request.get("password"));
        return ResponseEntity.ok(Map.of("message", "User registered successfully!"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        if (userService.authenticate(request.get("email"), request.get("password"))) {
            String token = jwtUtils.generateToken(request.get("email"));
            return ResponseEntity.ok(Map.of("success", true, "token", token));
        }
        return ResponseEntity.status(401).body(Map.of("message", "Invalid email or password"));
    }
}