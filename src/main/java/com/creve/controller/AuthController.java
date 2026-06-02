/*
package com.creve.controller;
import com.creve.model.User;
import com.creve.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap; // Zaroori import
import java.util.Map;     // Zaroori import

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    // Manual Register Endpoint - JSON Response ke saath
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");

        System.out.println("Registering: " + name);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully!");
        return ResponseEntity.ok(response); // Ab ye JSON format mein jayega
    }

    // Manual Login Endpoint - JSON Response ke saath
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        // Yahan login logic aayega...

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Login successful!");

        return ResponseEntity.ok(response); // Ab ye JSON format mein jayega
    }

    // Social Login
    @GetMapping("/login/success")
    public User loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");
        return userService.saveOrUpdateUser(name, email);
    }
}
*/
package com.creve.controller;

import com.creve.config.JwtUtils; // JWT import
import com.creve.model.User;
import com.creve.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    // 1. Manual Registration
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody Map<String, String> request) {
        String name = request.get("name");
        String email = request.get("email");
        String password = request.get("password"); // Password field add karna hoga

        // Yahan tum apna user save logic call karoge
        userService.saveOrUpdateUser(name, email);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully!");
        return ResponseEntity.ok(response);
    }

    // 2. Manual Login (Token return karega)
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        // Yahan database check lagao (UserService ke zariye)
        // Example: boolean isValid = userService.checkPassword(email, password);

        // Agar password sahi hai toh:
        String token = jwtUtils.generateToken(email);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("token", token); // Flutter app isi token ko save karega
        response.put("message", "Login successful!");

        return ResponseEntity.ok(response);
    }

    // 3. Social Login
    @GetMapping("/login/success")
    public ResponseEntity<Map<String, Object>> loginSuccess(@AuthenticationPrincipal OAuth2User principal) {
        String email = principal.getAttribute("email");
        String name = principal.getAttribute("name");

        userService.saveOrUpdateUser(name, email);

        String token = jwtUtils.generateToken(email);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", email);
        return ResponseEntity.ok(response);
    }
}