package com.creve.service;

import com.creve.model.User;
import com.creve.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Import zaroori hai
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    // Password encode karne ke liye
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 1. Register Logic
    public void registerUser(String name, String email, String password) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password)); // Password hash karke save karo
        userRepository.save(user);
    }

    // 2. Login Logic (Password check karne ke liye)
    public boolean authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // Database ka encoded password match karo
            return passwordEncoder.matches(password, user.getPassword());
        }
        return false;
    }

    // Existing method (Social login ke liye)
    public User saveOrUpdateUser(String name, String email) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        return existingUser.orElseGet(() -> {
            User newUser = new User();
            newUser.setName(name);
            newUser.setEmail(email);
            return userRepository.save(newUser);
        });
    }
}